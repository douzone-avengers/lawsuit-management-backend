package com.avg.lawsuitmanagement.file.service;

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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import static com.avg.lawsuitmanagement.common.exception.type.ErrorCode.FILE_NOT_FOUND;
import static com.avg.lawsuitmanagement.common.exception.type.ErrorCode.FILE_SAVE_FAIL;


@Service
@RequiredArgsConstructor
@Slf4j
public class FileService {

    private final FileMapperRepository fileMapperRepository;

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
        MultipartFile file = dto.getFileData();
        String fullFilePath = root + dto.getPath() + dto.getOriginFileName();

        try {
            byte[] fileData = file.getBytes();

            File outputFile = new File(fullFilePath);
            FileOutputStream outputStream = new FileOutputStream(outputFile);
            outputStream.write(fileData);
            outputStream.close();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CustomRuntimeException(FILE_SAVE_FAIL);
        }

        return fullFilePath;
    }

    public byte[] getFile(long fileId) {
        // fileId에 해당하는 파일을 읽어와서 fullFilePath찾기
        String fullFilePath = getFullFilePath(selectFileById(fileId));
        byte[] fileData = new byte[(int) new File(fullFilePath).length()];

        try {
            FileInputStream inputStream = new FileInputStream(fullFilePath);
            inputStream.read(fileData);
            inputStream.close();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CustomRuntimeException(FILE_NOT_FOUND);
        }

        return fileData;
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

//    public byte[] downloadImage(String fileName) {
//
//    }

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

    private String getFullFilePath (FileMetaDto dto) {
        return  root + dto.getPath() + dto.getOriginFileName();
    }
}
