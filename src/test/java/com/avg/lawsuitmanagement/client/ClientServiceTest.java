package com.avg.lawsuitmanagement.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.avg.lawsuitmanagement.client.dto.ClientDto;
import com.avg.lawsuitmanagement.client.repository.ClientMapperRepository;
import com.avg.lawsuitmanagement.client.repository.param.InsertClientParam;
import com.avg.lawsuitmanagement.client.service.ClientService;
import com.avg.lawsuitmanagement.common.custom.CustomRuntimeException;
import com.avg.lawsuitmanagement.common.exception.type.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class ClientServiceTest {

    @Autowired
    ClientService clientService;

    @Autowired
    ClientMapperRepository clientMapperRepository;


    @Test
    @Transactional
    @DisplayName("pk로 의뢰인 조회 성공")
    void getClientByIdSuccess() {
        //given
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
        long targetId = clientDto.getId();

        //when
        ClientDto dto = clientService.getClientById(targetId);

        //then
        assertEquals(email, dto.getEmail());
        assertEquals(name, dto.getName());
        assertEquals(phone, dto.getPhone());
        assertEquals(address, dto.getAddress());
    }

    @Test
    @Transactional
    @DisplayName("pk로 의뢰인 조회 실패 - 존재하지 않는 회원")
    void getClientByIdNotFound() {
        //given
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
        long targetId = clientDto.getId();

        //when
        CustomRuntimeException exception = assertThrows(CustomRuntimeException.class,
            () -> clientService.getClientById(targetId + 1));

        //then
        assertEquals(ErrorCode.CLIENT_NOT_FOUND,exception.getErrorCode());
    }

}
