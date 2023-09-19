package com.avg.lawsuitmanagement.file.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.avg.lawsuitmanagement.common.custom.CustomRuntimeException;
import com.avg.lawsuitmanagement.common.exception.type.ErrorCode;
import com.avg.lawsuitmanagement.expense.dto.ExpenseBillSelectDto;
import com.avg.lawsuitmanagement.expense.repository.param.ExpenseBillSelectParam;
import com.avg.lawsuitmanagement.expense.repository.param.ExpenseFileIdParam;
import com.avg.lawsuitmanagement.file.dto.FileDto;
import com.avg.lawsuitmanagement.file.dto.FileMetaDto;
import com.avg.lawsuitmanagement.file.repository.FileMapperRepository;
import com.avg.lawsuitmanagement.file.repository.param.FileInsertParam;
import com.avg.lawsuitmanagement.file.FileSaveDto;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import static com.avg.lawsuitmanagement.common.exception.type.ErrorCode.FILE_SAVE_FAIL;


@Service
@RequiredArgsConstructor
@Slf4j
public class FileService {

    private final FileMapperRepository fileMapperRepository;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final String root = "src/main/resources/file/";

    public String save(FileSaveDto dto) {
        String uuid = UUID.randomUUID().toString();
        String fullFilePath =
            root + dto.getDetailPath() + dto.getFileName() + uuid + "." + dto.getExtension();
        byte[] pdfData = Base64.getDecoder().decode(dto.getData());
        try {
            File outputFile = new File(fullFilePath);
            FileOutputStream outputStream = new FileOutputStream(outputFile);
            outputStream.write(pdfData);
            outputStream.close();
            log.info("파일생성 : " + fullFilePath);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CustomRuntimeException(ErrorCode.FILE_SAVE_FAIL);
        }
        return fullFilePath;
    }

    public String save(FileDto dto) {
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion("ap-northeast-2").build();
        MultipartFile multipartFile = dto.getFileData();
        String fullFilePath = dto.getPath() + dto.getOriginFileName() + "." + dto.getExtension();

        try {
            File file = File.createTempFile("temp", null);
            Files.copy(multipartFile.getInputStream(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            s3Client.putObject(new PutObjectRequest(bucket, fullFilePath, file)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
            removeFile(file);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CustomRuntimeException(FILE_SAVE_FAIL);
        }

        return fullFilePath;
    }

    private void removeFile(File targetFile) {
        if (targetFile.exists()) {
            if (targetFile.delete()) {
                log.info("파일이 삭제되었습니다.");
            } else {
                log.info("파일이 삭제되지 않았습니다.");
            }
        }
    }

    public void createFileAndInsertDataBase(FileDto dto) {
        // save() 파일 생성
        save(dto);

        // 파일 테이블에 삽입
        FileInsertParam param = FileInsertParam.of(dto);
        fileMapperRepository.insertFile(param);
    }

    public List<FileMetaDto> selectFileInfoListByExpenseId(ExpenseBillSelectDto dto) {
        ExpenseBillSelectParam param = ExpenseBillSelectParam.of(dto.getExpenseId(), dto.getPage(), dto.getCount());

        return fileMapperRepository.selectFileInfoListById(param);
    }

    public FileMetaDto selectFileByOriginFileName(String originFileName) {
        return fileMapperRepository.selectFileByOriginFileName(originFileName);
    }

    public FileMetaDto selectFileById(long fileId) {
        return fileMapperRepository.selectFileById(fileId);
    }

    @Transactional
    public void deleteFile(long fileId, long expenseId) {
        ExpenseFileIdParam param = ExpenseFileIdParam.of(expenseId, fileId);

        fileMapperRepository.deleteFile(fileId);
        fileMapperRepository.deleteExpenseFileMap(param);
    }

    public Long searchFileSize(Long expenseId) {
        return fileMapperRepository.searchCount(expenseId);
    }
}
