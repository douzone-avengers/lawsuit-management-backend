package com.avg.lawsuitmanagement.lawsuit.controller;

import com.avg.lawsuitmanagement.lawsuit.controller.form.InsertLawsuitForm;
import com.avg.lawsuitmanagement.lawsuit.dto.LawsuitDto;
import com.avg.lawsuitmanagement.lawsuit.service.LawsuitService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lawsuits")
public class LawsuitController {
    private final LawsuitService lawsuitService;

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
}
