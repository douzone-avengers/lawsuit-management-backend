package com.avg.lawsuitmanagement.token.controller;

import com.avg.lawsuitmanagement.token.controller.form.EmployeeLoginForm;
import com.avg.lawsuitmanagement.token.dto.JwtTokenDto;
import com.avg.lawsuitmanagement.token.controller.form.ClientLoginForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/token")
public class TokenController {

    @PostMapping("/client-login")
    public ResponseEntity<JwtTokenDto> clientLogin(@RequestBody ClientLoginForm clientLoginForm) {

        return null;
    }

    @PostMapping("/employee-login")
    public ResponseEntity<JwtTokenDto> employeeLogin(
        @RequestBody EmployeeLoginForm employeeLoginForm) {

        return null;
    }
}
