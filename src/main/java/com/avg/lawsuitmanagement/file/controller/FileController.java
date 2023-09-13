package com.avg.lawsuitmanagement.file.controller;

import com.avg.lawsuitmanagement.file.dto.FileMetaDto;
import com.avg.lawsuitmanagement.file.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
@Slf4j
public class FileController {
    private final FileService fileService;

    // 파일 다운로드
    @GetMapping("/download/{fileId}")
    public ResponseEntity<?> download(@PathVariable Long fileId) {
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

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition.builder("attachment").filename(fileMetaDto.getShowFileName() + "." + fileMetaDto.getExtension()).build());

        return ResponseEntity.status(HttpStatus.OK).contentType(mediaType).headers(headers).body(fileData);
    }
}
