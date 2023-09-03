package com.avg.lawsuitmanagement.client.service;

import static com.avg.lawsuitmanagement.common.exception.type.ErrorCode.CLIENT_ALREADY_EXIST;
import static com.avg.lawsuitmanagement.common.exception.type.ErrorCode.CLIENT_NOT_FOUND;

import com.avg.lawsuitmanagement.client.controller.form.InsertClientForm;
import com.avg.lawsuitmanagement.client.controller.form.UpdateClientInfoForm;
import com.avg.lawsuitmanagement.client.dto.ClientDto;
import com.avg.lawsuitmanagement.lawsuit.dto.ClientLawsuitCountDto;
import com.avg.lawsuitmanagement.client.repository.ClientMapperRepository;
import com.avg.lawsuitmanagement.client.repository.param.InsertClientParam;
import com.avg.lawsuitmanagement.client.repository.param.UpdateClientInfoParam;
import com.avg.lawsuitmanagement.common.custom.CustomRuntimeException;
import com.avg.lawsuitmanagement.lawsuit.repository.LawsuitMapperRepository;
import com.avg.lawsuitmanagement.lawsuit.service.LawsuitService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientService {

    private final ClientMapperRepository clientMapperRepository;
    private final LawsuitMapperRepository lawsuitMapperRepository;
    private final LawsuitService lawsuitService;

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

    @Transactional
    public void deleteClientInfo(long clientId) {
        ClientDto clientDto = clientMapperRepository.selectClientById(clientId);

        // 해당 clientId의 의뢰인이 없을 경우
        if (clientDto == null) {
            throw new CustomRuntimeException(CLIENT_NOT_FOUND);
        }

        clientMapperRepository.deleteClientInfo(clientId);

        // cliendId가 속한 사건별 사건id와 의뢰인 수
        List<ClientLawsuitCountDto> countClientList = lawsuitMapperRepository.selectLawsuitCountByClientId(clientId);

        for (ClientLawsuitCountDto dto : countClientList) {
            // 사건의 의뢰인 수가 1명(삭제하려는 의뢰인)일 때
            if (dto.getClientLawsuitCount() == 1) {
                lawsuitService.deleteLawsuitInfo(dto.getLawsuitId());
            } else {    // 사건의 의뢰인 수가 2명 이상일 때
                lawsuitMapperRepository.deleteLawsuitClientMapByClientId(clientId);
            }
        }
    }

    public List<ClientDto> selectClientList() {
        return clientMapperRepository.selectClientList();
    }
}