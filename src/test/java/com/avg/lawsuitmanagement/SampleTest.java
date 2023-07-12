package com.avg.lawsuitmanagement;

import com.avg.lawsuitmanagement.sample.SampleDto;
import com.avg.lawsuitmanagement.sample.SampleMapperRepository;
import com.avg.lawsuitmanagement.token.controller.form.ClientLoginForm;
import com.avg.lawsuitmanagement.token.repository.TokenMapperRepository;
import com.avg.lawsuitmanagement.token.repository.param.ClientSignUpParam;
import com.avg.lawsuitmanagement.token.service.TokenService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;


@SpringBootTest
public class SampleTest {


    @Autowired
    SampleMapperRepository sampleMapperRepository;

    @Autowired
    TokenMapperRepository tokenMapperRepository;

    @Autowired
    TokenService tokenService;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Test
    public void sampleTest() {
        sampleMapperRepository.save(new SampleDto(null, "abcd"));
    }

    @Test
    public void loginTest() {
        tokenService.clientLogin(ClientLoginForm.builder()
            .email("ksj2083@naver.com")
            .password("1")
            .build());
    }

    @Test
    public void insertClient() {
        tokenMapperRepository.insertClient(ClientSignUpParam.builder()
            .email("abc@naver.com")
            .password(passwordEncoder.encode("12341234"))
            .name("김네종")
            .phone("010")
            .address("부산시 남구")
            .build());

    }


}
