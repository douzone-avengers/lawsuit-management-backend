package com.avg.lawsuitmanagement.client.service;

import static com.avg.lawsuitmanagement.common.exception.type.ErrorCode.CLIENT_ALREADY_EXIST;
import static com.avg.lawsuitmanagement.common.exception.type.ErrorCode.CLIENT_NOT_FOUND;

import com.avg.lawsuitmanagement.client.controller.form.InsertClientForm;
import com.avg.lawsuitmanagement.client.controller.form.UpdateClientInfoForm;
import com.avg.lawsuitmanagement.client.dto.ClientDto;
import com.avg.lawsuitmanagement.client.repository.ClientMapperRepository;
import com.avg.lawsuitmanagement.client.repository.param.InsertClientParam;
import com.avg.lawsuitmanagement.client.repository.param.UpdateClientInfoParam;
import com.avg.lawsuitmanagement.common.custom.CustomRuntimeException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.HashMap;

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

    public void insertClient(InsertClientForm form) {
        ClientDto clientDto = clientMapperRepository.selectClientByEmail(form.getEmail());

        // 이미 등록된 고객이면
        if (clientDto != null) {
            throw new CustomRuntimeException(CLIENT_ALREADY_EXIST);
        }

        // 등록된 고객이 아니면 db 입력
        clientMapperRepository.insertClient(InsertClientParam.of(form));
    }

    public void updateClientInfo(long clientId, UpdateClientInfoForm form) {
        ClientDto clientDto = clientMapperRepository.selectClientById(clientId);

        // 해당 clientId의 의뢰인이 없을 경우
        if (clientDto == null) {
            throw new CustomRuntimeException(CLIENT_NOT_FOUND);
        }

        clientMapperRepository.updateClientInfo(UpdateClientInfoParam.of(clientId, form));
    }

    public void deleteClientInfo(long clientId) {
        ClientDto clientDto = clientMapperRepository.selectClientById(clientId);

        // 해당 clientId의 의뢰인이 없을 경우
        if (clientDto == null) {
            throw new CustomRuntimeException(CLIENT_NOT_FOUND);
        }

        clientMapperRepository.deleteClientInfo(clientId);
    }

    public List<ClientDto> getClientList() {
        return clientMapperRepository.getClientList();
    }
}
