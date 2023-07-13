package com.avg.lawsuitmanagement.token;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.avg.lawsuitmanagement.token.controller.form.ClientLoginForm;
import com.avg.lawsuitmanagement.token.controller.form.EmployeeLoginForm;
import com.avg.lawsuitmanagement.token.dto.JwtTokenDto;
import com.avg.lawsuitmanagement.token.repository.TokenMapperRepository;
import com.avg.lawsuitmanagement.token.repository.param.ClientSignUpParam;
import com.avg.lawsuitmanagement.token.repository.param.EmployeeSignUpParam;
import com.avg.lawsuitmanagement.token.service.TokenService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
//@TestPropertySource(locations = "classpath:application-test.properties")
public class TokenTest {

    @Autowired
    TokenMapperRepository tokenMapperRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    TokenService tokenService;


    @Test
    @Transactional
    @DisplayName("고객 로그인 성공")
    public void clientLoginSuccess() {

        String email = "coffee22@naver.com";
        String password = "efqfqdwqqwewqe";

        //given
        tokenMapperRepository.insertClient(ClientSignUpParam.builder()
            .email(email)
            .password(passwordEncoder.encode(password))
            .name("김커피")
            .phone("010-8635-2083")
            .address("부산시 남구")
            .build());

        //when
        JwtTokenDto jwtTokenDto = tokenService.clientLogin(ClientLoginForm.builder()
            .email(email)
            .password(password)
            .build());

        //then
        assertNotNull(jwtTokenDto);
        System.out.println(jwtTokenDto);
    }

    @Test
    @Transactional
    @DisplayName("고객 로그인 실패-계정불일치")
    public void clientLoginMismatch() {

        String email = "coffee22@naver.com";
        String password = "efqfqdwqqwewqe";

        //given
        tokenMapperRepository.insertClient(ClientSignUpParam.builder()
            .email(email)
            .password(passwordEncoder.encode(password))
            .name("김커피")
            .phone("01086352083")
            .address("부산시 남구")
            .build());

        //when, then
        assertThrows(RuntimeException.class,
            () -> tokenService.clientLogin(ClientLoginForm.builder()
                .email(email)
                .password(password + "1234")
                .build()));
    }

    @Test
    @Transactional
    @DisplayName("직원 로그인 성공")
    public void employeeLoginSuccess() {

        String email = "cola22@naver.com";
        String password = "12341234";

        //given
        tokenMapperRepository.insertEmployee(EmployeeSignUpParam.builder()
            .email(email)
            .password(passwordEncoder.encode(password))
            .name("김콜라")
            .phone("01086352083")
            .hierarchyId(3) //과장
            .address("부산 수영구")
            .roleId(2) //ADMIN
            .build());

        //when
        JwtTokenDto jwtTokenDto = tokenService.employeeLogin(EmployeeLoginForm.builder()
            .email(email)
            .password(password)
            .build());

        //then
        assertNotNull(jwtTokenDto);
        System.out.println(jwtTokenDto);
    }
}
