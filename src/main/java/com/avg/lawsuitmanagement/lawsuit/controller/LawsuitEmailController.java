package com.avg.lawsuitmanagement.lawsuit.controller;

import com.avg.lawsuitmanagement.common.util.SecurityUtil;
import com.avg.lawsuitmanagement.lawsuit.controller.form.SendLawsuitBookForm;
import com.avg.lawsuitmanagement.lawsuit.service.LawsuitPdfService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/emails/lawsuits")
public class LawsuitEmailController {

    private final LawsuitPdfService lawsuitPdfService;



//    @PostMapping("/{lawsuitId}/send-book")
//    public ResponseEntity<Void> makeAndSendLawsuitBook(@PathVariable Long lawsuitId,
//        @RequestBody @Valid
//        SendLawsuitBookForm form) {
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        authentication.getAuthorities()
//        //todo
//        return null;
//    }

}
