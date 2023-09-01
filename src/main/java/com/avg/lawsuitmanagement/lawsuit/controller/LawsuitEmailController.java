package com.avg.lawsuitmanagement.lawsuit.controller;

import com.avg.lawsuitmanagement.lawsuit.controller.form.SendLawsuitBookForm;
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

    @PostMapping("/{lawsuitId}/send-book")
    public ResponseEntity<Void> makeAndSendLawsuitBook(@PathVariable Long lawsuitId,
        @RequestBody @Valid
        SendLawsuitBookForm form) {

        //파일을 넘겨 받는다.
        //적당히 저장한다.
        //메일에 담아서 전송한다.
        //파일을 삭제한다.
        return null;
    }

}
