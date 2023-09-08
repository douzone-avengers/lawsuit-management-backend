package com.avg.lawsuitmanagement.file.controller;

import com.avg.lawsuitmanagement.file.dto.FileMetaDto;
import com.avg.lawsuitmanagement.file.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
@Slf4j
public class FileController {
    private final FileService fileService;

    @GetMapping("/download/{fileId}")
    public ResponseEntity<?> download(@PathVariable Long fileId) throws IOException {
        byte[] fileData = fileService.getFile(fileId);
        FileMetaDto fileMetaDto = fileService.selectFileById(fileId);
        // MIME 타입을 기본값으로 설정 (예: "application/octet-stream")
        MediaType mediaType = MediaType.parseMediaType("application/octet-stream");
        // 파일의 확장자를 확인하고 해당하는 MIME 타입으로 설정
        String extension = fileMetaDto.getExtension().toLowerCase();

        if (extension.equals("jpg") || extension.equals("jpeg")) {
            mediaType = MediaType.IMAGE_JPEG;
        } else if (extension.equals("png")) {
            mediaType = MediaType.IMAGE_PNG;
        } else if (extension.equals("gif")) {
            mediaType = MediaType.IMAGE_GIF;
        }

        return ResponseEntity.status(HttpStatus.OK).contentType(mediaType).body(fileData);
    }
}
