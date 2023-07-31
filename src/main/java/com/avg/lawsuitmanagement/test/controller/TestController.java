package com.avg.lawsuitmanagement.test.controller;

import com.avg.lawsuitmanagement.test.controller.form.TestForm;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TestController {

    @GetMapping("/test/param")
    public void validTest(@Valid TestForm form) {

        System.out.println(form);
        System.out.println("컨트롤러 도달");
    }

    @PostMapping("/test/body")
    public void validTest2(@Valid @RequestBody TestForm form) {

        System.out.println(form);
        System.out.println("컨트롤러 도달");
    }
}
