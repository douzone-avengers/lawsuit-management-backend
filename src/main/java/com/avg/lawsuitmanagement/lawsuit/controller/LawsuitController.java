package com.avg.lawsuitmanagement.lawsuit.controller;

import com.avg.lawsuitmanagement.lawsuit.controller.form.GetClientLawsuitForm;
import com.avg.lawsuitmanagement.lawsuit.controller.form.GetEmployeeLawsuitForm;
import com.avg.lawsuitmanagement.lawsuit.controller.form.InsertLawsuitForm;
import com.avg.lawsuitmanagement.lawsuit.controller.form.LawsuitCloseForm;
import com.avg.lawsuitmanagement.lawsuit.controller.form.UpdateLawsuitInfoForm;
import com.avg.lawsuitmanagement.lawsuit.dto.GetClientLawsuitListDto;
import com.avg.lawsuitmanagement.lawsuit.dto.GetEmployeeLawsuitListDto;
import com.avg.lawsuitmanagement.lawsuit.dto.LawsuitBasicDto;
import com.avg.lawsuitmanagement.lawsuit.dto.LawsuitPrintResponseDto;
import com.avg.lawsuitmanagement.lawsuit.service.LawsuitPdfService;
import com.avg.lawsuitmanagement.lawsuit.service.LawsuitService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lawsuits")
@Slf4j
public class LawsuitController {

    private final LawsuitService lawsuitService;
    private final LawsuitPdfService lawsuitPdfService;

    // 의뢰인 사건 리스트, 페이징 정보
    @GetMapping("/clients/{clientId}")
    public ResponseEntity<GetClientLawsuitListDto> selectClientLawsuitList(
        @PathVariable("clientId") Long clientId, @ModelAttribute GetClientLawsuitForm form) {
        GetClientLawsuitListDto getClientLawsuitListDto = lawsuitService.selectClientLawsuitList(
            clientId, form);

        return ResponseEntity.ok(getClientLawsuitListDto);
    }

    // 사원 별 사건 목록 조회
    @GetMapping("/employees/{employeeId}")
    public ResponseEntity<GetEmployeeLawsuitListDto> selectEmployeeLawsuitList(
        @PathVariable("employeeId") Long employeeId, @ModelAttribute GetEmployeeLawsuitForm form) {
        return ResponseEntity.ok(lawsuitService.selectEmployeeLawsuitList(employeeId, form));
    }

    // 사건 등록
    @PostMapping()
    public ResponseEntity<Void> insertLawsuit(@RequestBody @Valid InsertLawsuitForm form) {
        lawsuitService.insertLawsuit(form);
        return ResponseEntity.ok().build();
    }

    // 사건 수정
    @PutMapping("/{lawsuitId}")
    public ResponseEntity<Void> updateLawsuitInfo(@PathVariable("lawsuitId") Long lawsuitId,
        @RequestBody @Valid UpdateLawsuitInfoForm form) {
        lawsuitService.updateLawsuitInfo(lawsuitId, form);
        return ResponseEntity.ok().build();
    }

    // 사건 삭제
    @PatchMapping("/{lawsuitId}")
    public ResponseEntity<Void> deleteLawsuitInfo(@PathVariable("lawsuitId") Long lawsuitId) {
        lawsuitService.deleteLawsuitInfo(lawsuitId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{lawsuitId}/basic")
    public ResponseEntity<?> getBasicLawsuitInfo(@PathVariable Long lawsuitId) {
        LawsuitBasicDto result = lawsuitService.getBasicLawsuitInfo(lawsuitId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{lawsuitId}/print")
    public ResponseEntity<LawsuitPrintResponseDto> getPrintLawsuitInfo(
        @PathVariable Long lawsuitId) {
        LawsuitPrintResponseDto result = lawsuitPdfService.getPrintInfo(lawsuitId);
        return ResponseEntity.ok(result);
    }

    @PatchMapping("/closing")
    public ResponseEntity<?> closeLawsuit(
        @RequestBody LawsuitCloseForm form
    ) {
        LawsuitBasicDto result = lawsuitService.closeLawsuit(form);
        return ResponseEntity.ok().body(result);
    }

}
