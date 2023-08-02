package com.avg.lawsuitmanagement.promotion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.avg.lawsuitmanagement.client.dto.ClientDto;
import com.avg.lawsuitmanagement.client.repository.ClientMapperRepository;
import com.avg.lawsuitmanagement.client.repository.param.InsertClientParam;
import com.avg.lawsuitmanagement.client.repository.param.UpdateClientMemberIdParam;
import com.avg.lawsuitmanagement.common.custom.CustomRuntimeException;
import com.avg.lawsuitmanagement.common.exception.type.ErrorCode;
import com.avg.lawsuitmanagement.promotion.service.PromotionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class PromotionServiceTest {

    @Autowired
    PromotionService promotionService;

    @Autowired
    ClientMapperRepository clientMapperRepository;

    @Test
    @Transactional
    @DisplayName("의뢰인 promotion 키 발급 성공")
    void getClientPromotionKeySuccess() {

        //given
        long targetClientId = insertClientAndGetClientId();

        //when
        String key = promotionService.createClientPromotionKey(
            targetClientId);

        //then
        assertNotNull(key);
        assertEquals(10, key.length());
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
            () -> promotionService.createClientPromotionKey(
                targetClientId));

        //then
        assertEquals(ErrorCode.CLIENT_ALREADY_REGISTERED, exception.getErrorCode());
    }

    @Test
    @Transactional
    @DisplayName("의뢰인 promotion 키 resolve 성공")
    void resolveClientPromotionKeySuccess() {

        //given
        long targetClientId = insertClientAndGetClientId();
        String key = promotionService.createClientPromotionKey(
            targetClientId);

        //when
        ClientDto clientDto = promotionService.resolveClientPromotionKey(key);

        //then
        assertNotNull(clientDto);
        assertEquals("cofee123@naver.com", clientDto.getEmail());
    }

    @Test
    @Transactional
    @DisplayName("의뢰인 promotion 키 resolve 실패 - 존재하지 않는 promotion key")
    void resolveClientPromotionKeyNotFound() {

        //given
        long targetClientId = insertClientAndGetClientId();
        String key = promotionService.createClientPromotionKey(
            targetClientId);

        //when
        CustomRuntimeException exception = assertThrows(CustomRuntimeException.class,
            () -> promotionService.resolveClientPromotionKey(key + "abcd"));

        //then
        assertEquals(ErrorCode.PROMOTION_NOT_FOUND, exception.getErrorCode());
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
