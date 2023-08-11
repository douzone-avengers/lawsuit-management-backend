package com.avg.lawsuitmanagement.lawsuit.controller;

import com.avg.lawsuitmanagement.client.controller.form.GetClientLawsuitForm;
import com.avg.lawsuitmanagement.client.dto.ClientLawsuitDto;
import com.avg.lawsuitmanagement.lawsuit.controller.form.InsertLawsuitForm;
import com.avg.lawsuitmanagement.lawsuit.controller.form.UpdateLawsuitInfoForm;
import com.avg.lawsuitmanagement.lawsuit.dto.LawsuitDto;
import com.avg.lawsuitmanagement.lawsuit.service.LawsuitService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
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
public class LawsuitController {
    private final LawsuitService lawsuitService;

    // 의뢰인 사건 리스트, 페이징 정보
    @GetMapping("/clients/{clientId}")
    public ResponseEntity<ClientLawsuitDto> selectClientLawsuitList(
        @PathVariable("clientId") Long clientId,
        @ModelAttribute GetClientLawsuitForm form) {

        return ResponseEntity.ok(lawsuitService.selectClientLawsuitList(clientId, form));
    }

    // 사건 등록
    @PostMapping()
    public ResponseEntity<Void> insertLawsuit(@RequestBody @Valid InsertLawsuitForm form) {
        lawsuitService.insertLawsuit(form);
        return ResponseEntity.ok().build();
    }

    // 사건 목록 조회
    @GetMapping("/employees")
    public ResponseEntity<List<LawsuitDto>> selectLawsuitList() {
        return ResponseEntity.ok(lawsuitService.selectLawsuitList());
    }

    // 의뢰인에 대한 사건 조회
    @GetMapping("/{clientId}")
    public ResponseEntity<List<LawsuitDto>> selectLawsuitByClientId(@PathVariable("clientId") Long clientId) {
        return ResponseEntity.ok(lawsuitService.selectLawsuitByClientId(clientId));
    }

    // 사건 수정
    @PutMapping("/{lawsuitId}")
    public ResponseEntity<Void> updateLawsuitInfo(@PathVariable("lawsuitId") Long lawsuitId, @RequestBody @Valid UpdateLawsuitInfoForm form) {
        lawsuitService.updateLawsuitInfo(lawsuitId, form);
        return ResponseEntity.ok().build();
    }

    // 사건 삭제
    @PatchMapping("/{lawsuitId}")
    public ResponseEntity<Void> deleteLawsuitInfo(@PathVariable("lawsuitId") Long lawsuitId) {
        lawsuitService.deleteLawsuitInfo(lawsuitId);
        return ResponseEntity.ok().build();
    }

}
