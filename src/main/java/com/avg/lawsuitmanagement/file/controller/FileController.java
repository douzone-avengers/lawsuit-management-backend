package com.avg.lawsuitmanagement.file.controller;

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

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
@Slf4j
public class FileController {
    private final FileService fileService;

    @GetMapping("/download/{fileId}")
    public ResponseEntity<?> download(@PathVariable Long fileId) {
        byte[] fileData = fileService.getFile(fileId);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/jpg")).body(fileData);
    }
}
