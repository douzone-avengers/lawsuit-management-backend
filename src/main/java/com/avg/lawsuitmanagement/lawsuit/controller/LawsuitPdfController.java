package com.avg.lawsuitmanagement.lawsuit.controller;

import com.avg.lawsuitmanagement.lawsuit.dto.LawsuitPrintResponseDto;
import com.avg.lawsuitmanagement.lawsuit.service.LawsuitPdfService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pdf/lawsuits")
public class LawsuitPdfController {

    private final LawsuitPdfService lawsuitPdfService;

    @GetMapping("/{lawsuitId}/print")
    public ResponseEntity<?> getPrintLawsuitInfo(@PathVariable Long lawsuitId) {
        LawsuitPrintResponseDto result = lawsuitPdfService.getPrintInfo(lawsuitId);
        return ResponseEntity.ok(result);
    }

}
