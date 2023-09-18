package com.avg.lawsuitmanagement.advice.controller;

import com.avg.lawsuitmanagement.advice.controller.form.AdviceListForm;
import com.avg.lawsuitmanagement.advice.controller.form.InsertAdviceForm;
import com.avg.lawsuitmanagement.advice.controller.form.UpdateAdviceInfoForm;
import com.avg.lawsuitmanagement.advice.dto.AdviceDetailResponseDto;
import com.avg.lawsuitmanagement.advice.dto.GetAdviceListDto;
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
    public ResponseEntity<AdviceDetailResponseDto> getAdviceInfo(@PathVariable("adviceId") Long adviceId){
        AdviceDetailResponseDto result = adviceService.getAdviceInfo(adviceId);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/lawsuits/{lawsuitId}")
    public ResponseEntity<GetAdviceListDto> selectAdviceListInfo(@ModelAttribute AdviceListForm form, @PathVariable ("lawsuitId") Long lawsuitId){

        return ResponseEntity.ok(adviceService.getAdviceByLawsuitId(form,lawsuitId));
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
