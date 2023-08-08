package com.avg.lawsuitmanagement.advice.controller;

import com.avg.lawsuitmanagement.advice.controller.form.InsertAdviceForm;
import com.avg.lawsuitmanagement.advice.controller.form.UpdateAdviceInfoForm;
import com.avg.lawsuitmanagement.advice.dto.AdviceDto;
import com.avg.lawsuitmanagement.advice.service.AdviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/advices")
public class AdviceController {

    private final AdviceService adviceService;

    //상담 상세 정보
    @GetMapping("/{adviceId}")
    public ResponseEntity<AdviceDto> selectAdviceDetailInfo(@PathVariable("adviceId") Long adviceId) {
        return ResponseEntity.ok(adviceService.getAdviceById(adviceId));
    }

    // 상담 등록
    @PostMapping()
    public ResponseEntity<Void> insertAdvice(@RequestBody @Valid InsertAdviceForm form){
        adviceService.insertAdvice(form);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{adviceId}")
    public ResponseEntity<Void> updateAdvice(@PathVariable("adviceId") Long adviceId, @RequestBody @Valid UpdateAdviceInfoForm form){
        adviceService.updateAdviceInfo(adviceId, form);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{adviceId}")
    public ResponseEntity<Void> deleteAdviceInfo(@PathVariable("adviceId") Long adviceId){
        adviceService.deleteAdviceInfo(adviceId);
        return ResponseEntity.ok().build();
    }

}