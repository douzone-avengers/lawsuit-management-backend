package com.avg.lawsuitmanagement.member;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.avg.lawsuitmanagement.client.dto.ClientDto;
import com.avg.lawsuitmanagement.client.repository.ClientMapperRepository;
import com.avg.lawsuitmanagement.client.repository.param.InsertClientParam;
import com.avg.lawsuitmanagement.client.repository.param.UpdateClientMemberIdParam;
import com.avg.lawsuitmanagement.common.custom.CustomRuntimeException;
import com.avg.lawsuitmanagement.common.exception.type.ErrorCode;
import com.avg.lawsuitmanagement.member.dto.CreatePromotionKeyDto;
import com.avg.lawsuitmanagement.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    ClientMapperRepository clientMapperRepository;

    @Test
    @Transactional
    @DisplayName("의뢰인 promotion 키 발급 성공")
    void getClientPromotionKeySuccess() {

        //given
        long targetClientId = insertClientAndGetClientId();

        //when
        CreatePromotionKeyDto dto = memberService.getClientPromotionKey(
            targetClientId);

        //then
        assertNotNull(dto);
        assertEquals(10, dto.getValue().length());
    }

    @Test
    @Transactional
    @DisplayName("의뢰인 promotion 키 발급 실패 - 이미 가입한 의뢰인")
    void getClientPromotionKeyAlreadyRegisteredClient() {

        //given
        long targetClientId = insertClientAndGetClientId();
        clientMapperRepository.updateClientMemberId(UpdateClientMemberIdParam.builder()
            .memberId(12L)
            .clientId(targetClientId)
            .build());

        //when
        CustomRuntimeException exception = assertThrows(CustomRuntimeException.class,
            () -> memberService.getClientPromotionKey(
                targetClientId));

        //then
        assertEquals(ErrorCode.CLIENT_ALREADY_REGISTERED,exception.getErrorCode());
    }

    private long insertClientAndGetClientId() {
        String email = "cofee123@naver.com";
        String name = "김커피";
        String phone = "010-1564-4848";
        String address = "커피하우스";
        clientMapperRepository.insertClient(InsertClientParam.builder()
            .email(email)
            .name(name)
            .phone(phone)
            .address(address)
            .build()
        );

        ClientDto clientDto = clientMapperRepository.selectClientByEmail(email);
        return clientDto.getId();
    }
}
