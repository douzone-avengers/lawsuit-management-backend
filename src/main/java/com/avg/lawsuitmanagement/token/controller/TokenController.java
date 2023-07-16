package com.avg.lawsuitmanagement.token.controller;

import com.avg.lawsuitmanagement.token.controller.form.LoginForm;
import com.avg.lawsuitmanagement.token.dto.JwtTokenDto;
import com.avg.lawsuitmanagement.token.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/token")
@Slf4j
public class TokenController {

    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<JwtTokenDto> clientLogin(@RequestBody LoginForm loginForm) {

        JwtTokenDto jwtTokenDto = tokenService.login(loginForm);
        log.info(loginForm.getEmail() + " 님 로그인 성공");
        log.info("발행된 토큰 : \n" + jwtTokenDto.toString());

        return ResponseEntity.ok(jwtTokenDto);
    }
}
