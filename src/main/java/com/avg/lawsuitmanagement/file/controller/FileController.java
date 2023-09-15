package com.avg.lawsuitmanagement.file.controller;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.avg.lawsuitmanagement.file.dto.FileMetaDto;
import com.avg.lawsuitmanagement.file.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
@Slf4j
public class FileController {
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    private final FileService fileService;

    // 파일 다운로드
    @GetMapping("/download/{fileId}")
    public ResponseEntity<?> download(@PathVariable Long fileId) {
        FileMetaDto fileMetaDto = fileService.selectFileById(fileId);

        AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion("ap-northeast-2").build();
        UrlResource urlResource = new UrlResource(s3Client.getUrl(bucket, fileMetaDto.getShowFileName()));

        // MIME 타입을 기본값으로 설정 (예: "application/octet-stream")
        MediaType mediaType = MediaType.parseMediaType("application/octet-stream;charset=UTF-8");
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
        headers.add("Access-Control-Expose-Headers", "Content-Disposition");
        headers.setContentDisposition(ContentDisposition.builder("attachment")
                .filename(fileMetaDto.getShowFileName() + "." + fileMetaDto.getExtension(), StandardCharsets.UTF_8)
                .build());

        return ResponseEntity.status(HttpStatus.OK).contentType(mediaType).headers(headers).body(urlResource);
    }
}
