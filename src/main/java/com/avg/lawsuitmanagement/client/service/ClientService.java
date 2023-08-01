package com.avg.lawsuitmanagement.client.service;

import static com.avg.lawsuitmanagement.common.exception.type.ErrorCode.CLIENT_NOT_FOUND;

import com.avg.lawsuitmanagement.client.dto.ClientDto;
import com.avg.lawsuitmanagement.client.repository.ClientMapperRepository;
import com.avg.lawsuitmanagement.common.custom.CustomRuntimeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientService {

    private final ClientMapperRepository clientMapperRepository;

    public ClientDto getClientById(long clientId) {
        ClientDto clientDto = clientMapperRepository.selectClientById(clientId);
        //없을 경우
        if(clientDto == null) {
            throw new CustomRuntimeException(CLIENT_NOT_FOUND);
        }

        return clientDto;
    }
}
