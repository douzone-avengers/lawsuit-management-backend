package com.avg.lawsuitmanagement.file.service;

import com.avg.lawsuitmanagement.common.custom.CustomRuntimeException;
import com.avg.lawsuitmanagement.common.exception.type.ErrorCode;
import com.avg.lawsuitmanagement.file.FileSaveDto;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Base64;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class FileService {

    private final String root = "src/main/resources/file/";

    public String save(FileSaveDto dto) {
        String fullFilePath = root+dto.getDetailPath()+dto.getFileName();
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
}
