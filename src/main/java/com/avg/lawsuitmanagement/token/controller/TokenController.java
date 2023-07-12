package com.avg.lawsuitmanagement.token.controller;

import com.avg.lawsuitmanagement.token.controller.form.EmployeeLoginForm;
import com.avg.lawsuitmanagement.token.dto.JwtTokenDto;
import com.avg.lawsuitmanagement.token.controller.form.ClientLoginForm;
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

    @PostMapping("/client-login")
    public ResponseEntity<JwtTokenDto> clientLogin(@RequestBody ClientLoginForm clientLoginForm) {

        JwtTokenDto jwtTokenDto = tokenService.clientLogin(clientLoginForm);

        log.info(clientLoginForm.getEmail() + " 님 토큰 발행 성공, 발행된 토큰 : ");
        log.info(jwtTokenDto.toString());
        return ResponseEntity.ok(jwtTokenDto);
    }

    @PostMapping("/employee-login")
    public ResponseEntity<JwtTokenDto> employeeLogin(
        @RequestBody EmployeeLoginForm employeeLoginForm) {

        return null;
    }
}
