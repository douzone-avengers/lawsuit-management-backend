package com.avg.lawsuitmanagement.client.service;

import static com.avg.lawsuitmanagement.common.exception.type.ErrorCode.CLIENT_NOT_FOUND;
import static com.avg.lawsuitmanagement.common.exception.type.ErrorCode.EMAIL_ALREADY_EXIST;
import static com.avg.lawsuitmanagement.common.exception.type.ErrorCode.SIGNED_CLIENT_CANNOT_DELETE;

import com.avg.lawsuitmanagement.client.controller.form.InsertClientForm;
import com.avg.lawsuitmanagement.client.controller.form.UpdateClientInfoForm;
import com.avg.lawsuitmanagement.client.dto.ClientDto;
import com.avg.lawsuitmanagement.client.repository.ClientMapperRepository;
import com.avg.lawsuitmanagement.client.repository.param.InsertClientParam;
import com.avg.lawsuitmanagement.client.repository.param.ReRegisterClientParam;
import com.avg.lawsuitmanagement.client.repository.param.UpdateClientInfoParam;
import com.avg.lawsuitmanagement.common.custom.CustomRuntimeException;
import com.avg.lawsuitmanagement.lawsuit.dto.ClientLawsuitCountDto;
import com.avg.lawsuitmanagement.lawsuit.repository.LawsuitMapperRepository;
import com.avg.lawsuitmanagement.lawsuit.service.LawsuitService;
import com.avg.lawsuitmanagement.member.dto.MemberDto;
import com.avg.lawsuitmanagement.member.repository.MemberMapperRepository;
import com.avg.lawsuitmanagement.member.service.LoginUserInfoService;
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
    private final MemberMapperRepository memberMapperRepository;
    private final LawsuitMapperRepository lawsuitMapperRepository;
    private final LawsuitService lawsuitService;
    private final LoginUserInfoService loginUserInfoService;

    public ClientDto getLoginClientInfo() {

        MemberDto loginMemberInfo = loginUserInfoService.getLoginMemberInfo();
        return getClientByMemberId(loginMemberInfo.getId());
    }

    public ClientDto getClientByMemberId(long memberId) {
        ClientDto clientDto = clientMapperRepository.selectClientByMemberId(memberId);
        if(clientDto == null) {
            throw new CustomRuntimeException(CLIENT_NOT_FOUND);
        }

        return clientDto;
    }

    public ClientDto getClientById(long clientId) {
        ClientDto clientDto = clientMapperRepository.selectClientById(clientId);
        //없을 경우
        if (clientDto == null) {
            throw new CustomRuntimeException(CLIENT_NOT_FOUND);
        }

        return clientDto;
    }

    public void insertClient(InsertClientForm form) {
        //예전에 삭제된 메일이었을 경우
        ClientDto deletedClientInfo = clientMapperRepository.selectDeletedClientByEmail(form.getEmail());
        if(deletedClientInfo != null) {
            clientMapperRepository.reRegisterClient(ReRegisterClientParam.of(
                deletedClientInfo.getId(), form));
            return;
        }

        checkEmailDuplicate(form.getEmail());
        clientMapperRepository.insertClient(InsertClientParam.of(form));
    }

    private void checkEmailDuplicate(String email) {
        if (clientMapperRepository.selectClientByEmailContainDeleted(email) != null) {
            throw new CustomRuntimeException(EMAIL_ALREADY_EXIST);
        }
        if (memberMapperRepository.selectMemberByEmailContainDeleted(email) != null) {
            throw new CustomRuntimeException(EMAIL_ALREADY_EXIST);
        }
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

        //회원가입 된 의뢰인일 경우
        if(clientDto.getMemberId() == 0) {
            throw new CustomRuntimeException(SIGNED_CLIENT_CANNOT_DELETE);
        }

        clientMapperRepository.deleteClientInfo(clientId);

        // cliendId가 속한 사건별 사건id와 의뢰인 수
        List<ClientLawsuitCountDto> countClientList = lawsuitMapperRepository.selectLawsuitCountByClientId(
            clientId);

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

    public ClientDto selectClientIdByEmail(String email) {
        return clientMapperRepository.selectClientByEmail(email);
    }
}