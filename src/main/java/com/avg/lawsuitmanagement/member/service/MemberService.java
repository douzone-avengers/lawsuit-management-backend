package com.avg.lawsuitmanagement.member.service;

import com.avg.lawsuitmanagement.client.dto.ClientDto;
import com.avg.lawsuitmanagement.client.service.ClientService;
import com.avg.lawsuitmanagement.common.custom.CustomRuntimeException;
import com.avg.lawsuitmanagement.common.exception.type.ErrorCode;
import com.avg.lawsuitmanagement.common.util.SecurityUtil;
import com.avg.lawsuitmanagement.member.dto.MemberDto;
import com.avg.lawsuitmanagement.member.dto.CreatePromotionKeyDto;
import com.avg.lawsuitmanagement.member.repository.MemberMapperRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberMapperRepository memberMapperRepository;
    private final ClientService clientService;

    public MemberDto getLoginMemberInfo() {
        String email = SecurityUtil.getCurrentLoginEmail();
        return memberMapperRepository.selectMemberByEmail(email);
    }

    @Transactional
    public CreatePromotionKeyDto getClientPromotionKey(long clientId) {
        String promotionKey = getRandomPromotionKey();

        //존재하는 유저인지? -> ClientService에서 검증
        //이미 가입된 의뢰인인지?
        ClientDto clientDto = clientService.getClientById(clientId);
        if(clientDto.getMemberId() != 0) {
            throw new CustomRuntimeException(ErrorCode.CLIENT_ALREADY_REGISTERED);
        }

        //db 입력
        memberMapperRepository.insertPromotionKey(promotionKey);

        //return
        return CreatePromotionKeyDto.builder()
            .value(promotionKey)
            .build();
    }


    private String getRandomPromotionKey() {
        return RandomStringUtils.randomAlphabetic(10);
    }
}
