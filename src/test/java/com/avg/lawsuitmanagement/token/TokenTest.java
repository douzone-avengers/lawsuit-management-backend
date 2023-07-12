package com.avg.lawsuitmanagement.token;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.avg.lawsuitmanagement.token.controller.form.ClientLoginForm;
import com.avg.lawsuitmanagement.token.dto.JwtTokenDto;
import com.avg.lawsuitmanagement.token.repository.TokenMapperRepository;
import com.avg.lawsuitmanagement.token.repository.param.ClientSignUpParam;
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
            .phone("010-8635-2083")
            .address("부산시 남구")
            .build());

        //when, then
        assertThrows(RuntimeException.class,
            () -> tokenService.clientLogin(ClientLoginForm.builder()
                .email(email)
                .password(password + "1234")
                .build()));
    }
}
