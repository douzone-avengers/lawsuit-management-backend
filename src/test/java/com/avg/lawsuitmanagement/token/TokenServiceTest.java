package com.avg.lawsuitmanagement.token;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.avg.lawsuitmanagement.member.repository.MemberMapperRepository;
import com.avg.lawsuitmanagement.token.controller.form.LoginForm;
import com.avg.lawsuitmanagement.token.dto.JwtTokenDto;
import com.avg.lawsuitmanagement.token.dto.RefreshTokenDto;
import com.avg.lawsuitmanagement.token.repository.TokenMapperRepository;
import com.avg.lawsuitmanagement.member.repository.param.SignUpParam;
import com.avg.lawsuitmanagement.token.service.TokenService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
//@TestPropertySource(locations = "classpath:application-test.properties")
public class TokenServiceTest {

    @Autowired
    TokenMapperRepository tokenMapperRepository;
    @Autowired
    MemberMapperRepository memberMapperRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    TokenService tokenService;

    @Test
    @Transactional
    @DisplayName("로그인 성공")
    public void loginSuccess() {

        String email = "coffee22@naver.com";
        String password = "efqfqdwqqwewqe";
        String name = "김커피";
        String phone = "010-1234-5678";
        String address = "부산시 수영구";
        long hierarchyId = 2L; //staff
        long roldId = 2L; //employee

        //given
        memberMapperRepository.insertMember(SignUpParam.builder()
            .email(email)
            .password(passwordEncoder.encode(password))
            .name(name)
            .phone(phone)
            .address(address)
            .hierarchyId(hierarchyId)
            .roleId(roldId)
            .build());

        //when
        JwtTokenDto tokenDto = tokenService.login(LoginForm.builder()
            .email(email)
            .password(password)
            .build());

        //then
        assertNotNull(tokenDto);

        RefreshTokenDto refreshTokenDto = tokenMapperRepository.selectRefreshTokenByKey(email);

        System.out.println("발급 토큰 : " + tokenDto);
        System.out.println("저장된 refresh 토큰 : " + refreshTokenDto);
    }

    @Test
    @Transactional
    @DisplayName("로그인 실패 - 계정정보 불일치")
    public void loginFailMismatch() {

        String email = "coffee22@naver.com";
        String password = "efqfqdwqqwewqe";
        String name = "김커피";
        String phone = "010-1234-5678";
        String address = "부산시 수영구";
        long hierarchyId = 2L; //staff
        long roldId = 2L; //employee

        //given
        memberMapperRepository.insertMember(SignUpParam.builder()
            .email(email)
            .password(passwordEncoder.encode(password))
            .name(name)
            .phone(phone)
            .address(address)
            .hierarchyId(hierarchyId)
            .roleId(roldId)
            .build());

        //when, then
        assertThrows(RuntimeException.class,
            () -> tokenService.login(LoginForm.builder()
                .email(email)
                .password(password + "1234")
                .build()));
    }
}
