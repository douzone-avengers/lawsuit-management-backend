package com.avg.lawsuitmanagement.token.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TestController {

    @GetMapping("/test")
    public void testMethod() {

        System.out.println("컨트롤러 도달");
    }

    @GetMapping("/test2")
    public void testMethod2() {
//        throw new CustomRuntimeException(ErrorCode.TOKEN_NOT_FOUND);
        System.out.println("컨트롤러 도달");
    }


}
