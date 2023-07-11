package com.avg.lawsuitmanagement.token.service;

import com.avg.lawsuitmanagement.token.TokenProvider;
import com.avg.lawsuitmanagement.token.controller.form.ClientLoginForm;
import com.avg.lawsuitmanagement.token.dto.ClientDto;
import com.avg.lawsuitmanagement.token.repository.TokenMapperRepository;
import com.avg.lawsuitmanagement.token.repository.param.ClientLoginParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenService {

    private final TokenMapperRepository tokenMapperRepository;
    private final TokenProvider tokenProvider;

    public void clientLogin(ClientLoginForm form) {

        ClientDto clientDto = tokenMapperRepository.selectClientByIdAndPassword(
            ClientLoginParam.of(form));

        log.info(clientDto.toString());

    }

}
