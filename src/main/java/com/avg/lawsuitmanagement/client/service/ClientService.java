package com.avg.lawsuitmanagement.client.service;

import static com.avg.lawsuitmanagement.common.exception.type.ErrorCode.CLIENT_ALREADY_EXIST;
import static com.avg.lawsuitmanagement.common.exception.type.ErrorCode.CLIENT_NOT_FOUND;

import com.avg.lawsuitmanagement.client.controller.form.GetClientLawsuitForm;
import com.avg.lawsuitmanagement.client.controller.form.InsertClientForm;
import com.avg.lawsuitmanagement.client.controller.form.UpdateClientInfoForm;
import com.avg.lawsuitmanagement.client.dto.ClientDto;
import com.avg.lawsuitmanagement.client.dto.ClientLawsuitDto;
import com.avg.lawsuitmanagement.client.repository.ClientMapperRepository;
import com.avg.lawsuitmanagement.client.repository.param.InsertClientParam;
import com.avg.lawsuitmanagement.client.repository.param.SelectClientLawsuitListParam;
import com.avg.lawsuitmanagement.client.repository.param.UpdateClientInfoParam;
import com.avg.lawsuitmanagement.common.custom.CustomRuntimeException;
import com.avg.lawsuitmanagement.common.util.PagingUtil;
import com.avg.lawsuitmanagement.common.util.dto.PageRangeDto;
import com.avg.lawsuitmanagement.common.util.dto.PagingDto;
import com.avg.lawsuitmanagement.lawsuit.dto.LawsuitDto;
import java.util.List;
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

    public List<ClientDto> selectClientList() {
        return clientMapperRepository.selectClientList();
    }

    public ClientLawsuitDto selectClientLawsuitList(long clientId, GetClientLawsuitForm form) {
        ClientDto clientDto = clientMapperRepository.selectClientById(clientId);

        // 해당 clientId의 의뢰인이 없을 경우
        if (clientDto == null) {
            throw new CustomRuntimeException(CLIENT_NOT_FOUND);
        }

        long total = clientMapperRepository.getLawsuitCountByClientId(clientId);

        PagingDto pagingDto = PagingUtil.calculatePaging(form.getCurPage(), form.getItemsPerPage());
        SelectClientLawsuitListParam param = SelectClientLawsuitListParam.of(clientId, pagingDto);
        // 한 페이지에 나타나는 사건 리스트 목록
        List<LawsuitDto> lawsuitList = clientMapperRepository.selectClientLawsuitList(param);

        // startPage, endPage 저장
        PageRangeDto pageRangeDto = PagingUtil.calculatePageRange(form.getCurPage(), total);

        return ClientLawsuitDto.of(lawsuitList, pageRangeDto);
    }
}
