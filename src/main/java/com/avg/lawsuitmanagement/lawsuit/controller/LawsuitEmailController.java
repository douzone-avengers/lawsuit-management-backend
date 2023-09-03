package com.avg.lawsuitmanagement.lawsuit.controller;

import com.avg.lawsuitmanagement.lawsuit.controller.form.SendLawsuitBookForm;
import com.avg.lawsuitmanagement.lawsuit.service.LawsuitService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/emails/lawsuits")
public class LawsuitEmailController {

    private final LawsuitService lawsuitService;

    @PostMapping("/{lawsuitId}/book")
    public ResponseEntity<Void> sendLawsuitBook(@PathVariable Long lawsuitId,
        @RequestBody @Valid
        SendLawsuitBookForm form) {

        lawsuitService.saveAndSendLawsuitBook(form, lawsuitId);
        return ResponseEntity.ok().build();
    }
}
