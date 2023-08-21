package com.avg.lawsuitmanagement.member;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.avg.lawsuitmanagement.client.dto.ClientDto;
import com.avg.lawsuitmanagement.client.repository.ClientMapperRepository;
import com.avg.lawsuitmanagement.client.repository.param.InsertClientParam;
import com.avg.lawsuitmanagement.client.repository.param.UpdateClientMemberIdParam;
import com.avg.lawsuitmanagement.client.service.ClientService;
import com.avg.lawsuitmanagement.common.custom.CustomRuntimeException;
import com.avg.lawsuitmanagement.common.exception.type.ErrorCode;
import com.avg.lawsuitmanagement.member.controller.form.ClientSignUpForm;
import com.avg.lawsuitmanagement.member.controller.form.EmployeeSignUpForm;
import com.avg.lawsuitmanagement.member.dto.MemberDto;
import com.avg.lawsuitmanagement.member.repository.MemberMapperRepository;
import com.avg.lawsuitmanagement.member.repository.param.InsertMemberParam;
import com.avg.lawsuitmanagement.member.service.MemberService;
import com.avg.lawsuitmanagement.promotion.dto.ClientPromotionKeyDto;
import com.avg.lawsuitmanagement.promotion.dto.EmployeePromotionKeyDto;
import com.avg.lawsuitmanagement.promotion.repository.PromotionMapperRepository;
import com.avg.lawsuitmanagement.promotion.service.PromotionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class MemberServiceTest {

    @Autowired
    ClientService clientService;
    @Autowired
    ClientMapperRepository clientMapperRepository;
    @Autowired
    MemberService memberService;
    @Autowired
    PromotionService promotionService;
    @Autowired
    MemberMapperRepository memberMapperRepository;
    @Autowired
    PromotionMapperRepository promotionMapperRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    @Transactional
    @DisplayName("의뢰인 회원가입 성공")
    void clientSignUpSuccess() {

        //given
        String email = "cofee123@naver.com";
        String password = "1234";
        String name = "김커피";
        String phone = "010-1564-4848";
        String address = "커피하우스";

        InsertClientParam param = InsertClientParam.builder()
            .email(email)
            .name(name + "123") //회원가입 정보와는 달라도 된다.
            .phone(phone)
            .address(address)
            .build();

        long clientId = insertClientAndGetClientId(param);
        String promotionKey = promotionService.createClientPromotionKey(
            clientId, false);

        //when
        memberService.clientSignUp(ClientSignUpForm.builder()
            .promotionKey(promotionKey)
            .email(email)
            .password(password)
            .name(name)
            .phone(phone)
            .address(address)
            .build());

        //then
        MemberDto memberDto = memberMapperRepository.selectMemberByEmail(email);
        ClientPromotionKeyDto clientPromotionKeyDto = promotionMapperRepository.selectPromotionKeyByValue(
            promotionKey);
        ClientDto clientDto = clientMapperRepository.selectClientById(clientId);

        //member 테이블 검증
        assertNotNull(memberDto);
        assertEquals(name, memberDto.getName());
        assertEquals(email, memberDto.getEmail());
        assertEquals(phone, memberDto.getPhone());
        assertEquals(address, memberDto.getAddress());
        assertEquals(clientDto.getMemberId(), memberDto.getId());

        //client_promotion 검증
        assertFalse(clientPromotionKeyDto.isActive());
    }

    @Test
    @Transactional
    @DisplayName("의뢰인 회원가입 실패 - 이미 가입된 의뢰인")
    void clientSignUpAlreadyRegistered() {

        //given
        String email = "cofee123@naver.com";
        String password = "1234";
        String name = "김커피";
        String phone = "010-1564-4848";
        String address = "커피하우스";

        InsertClientParam param = InsertClientParam.builder()
            .email(email)
            .name(name + "123") //회원가입 정보와는 달라도 된다.
            .phone(phone)
            .address(address)
            .build();

        long clientId = insertClientAndGetClientId(param);
        String promotionKey = promotionService.createClientPromotionKey(
            clientId, false);
        clientMapperRepository.updateClientMemberId(UpdateClientMemberIdParam.builder()
            .clientId(clientId)
            .memberId(33)
            .build());

        //when
        CustomRuntimeException exception = assertThrows(CustomRuntimeException.class,
            () -> memberService.clientSignUp(ClientSignUpForm.builder()
                .promotionKey(promotionKey)
                .email(email)
                .password(password)
                .name(name)
                .phone(phone)
                .address(address)
                .build()));

        //then
        assertEquals(ErrorCode.CLIENT_ALREADY_REGISTERED, exception.getErrorCode());
    }

    @Test
    @Transactional
    @DisplayName("의뢰인 회원가입 실패 - 이메일 중복")
    void clientSignUpEmailAlreadyExist() {
        //given
        String email = "cofee123@naver.com";
        String password = "1234";
        String name = "김커피";
        String phone = "010-1564-4848";
        String address = "커피하우스";

        InsertClientParam param = InsertClientParam.builder()
            .email(email)
            .name(name + "123") //회원가입 정보와는 달라도 된다.
            .phone(phone)
            .address(address)
            .build();

        long clientId = insertClientAndGetClientId(param);
        String promotionKey = promotionService.createClientPromotionKey(
            clientId, false);

        memberMapperRepository.insertMember(InsertMemberParam.builder()
            .email(email)
            .password(passwordEncoder.encode("1111"))
            .name("김길동")
            .phone("010-1111-5555")
            .hierarchyId(1)
            .address("주소123")
            .roleId(1)
            .build());

        //when
        CustomRuntimeException exception = assertThrows(CustomRuntimeException.class,
            () -> memberService.clientSignUp(ClientSignUpForm.builder()
                .promotionKey(promotionKey)
                .email(email)
                .password(password)
                .name(name)
                .phone(phone)
                .address(address)
                .build()));

        //then
        assertEquals(ErrorCode.MEMBER_EMAIL_ALREADY_EXIST, exception.getErrorCode());
    }

    private long insertClientAndGetClientId(InsertClientParam param) {

        clientMapperRepository.insertClient(param);
        ClientDto clientDto = clientMapperRepository.selectClientByEmail(param.getEmail());
        return clientDto.getId();
    }

    @Test
    @Transactional
    @DisplayName("직원 회원가입 성공")
    void EmployeeSignUpSuccess() {
        //given
        String email = "cofee123@naver.com";
        String password = "1234";
        String name = "김커피";
        String phone = "010-1564-4848";
        String address = "커피하우스";
        long hierarchyId = 2L;
        long roleId = 2L;

        String promotionKey = promotionService.createEmployeePromotionKey();

        //when
        memberService.employeeSignUp(EmployeeSignUpForm.builder()
            .promotionKey(promotionKey)
            .email(email)
            .password(password)
            .name(name)
            .phone(phone)
            .address(address)
            .hierarchyId(hierarchyId)
            .roleId(roleId)
            .build()
        );

        //then
        MemberDto memberDto = memberMapperRepository.selectMemberByEmail(email);
        EmployeePromotionKeyDto employeePromotionKeyDto = promotionMapperRepository.selectEmployeePromotionKeyByValue(
            promotionKey);

        //member 테이블 검증
        assertNotNull(memberDto);
        assertEquals(name, memberDto.getName());
        assertEquals(email, memberDto.getEmail());
        assertEquals(phone, memberDto.getPhone());
        assertEquals(address, memberDto.getAddress());

        //employee_promotion 검증
        assertFalse(employeePromotionKeyDto.isActive());
    }

}
