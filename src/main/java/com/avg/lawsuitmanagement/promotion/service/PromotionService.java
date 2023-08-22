package com.avg.lawsuitmanagement.promotion.service;

import static com.avg.lawsuitmanagement.common.exception.type.ErrorCode.PROMOTION_NOT_ACTIVE;
import static com.avg.lawsuitmanagement.common.exception.type.ErrorCode.PROMOTION_NOT_FOUND;

import com.avg.lawsuitmanagement.client.dto.ClientDto;
import com.avg.lawsuitmanagement.client.service.ClientService;
import com.avg.lawsuitmanagement.common.custom.CustomRuntimeException;
import com.avg.lawsuitmanagement.common.exception.type.ErrorCode;
import com.avg.lawsuitmanagement.common.util.SecurityUtil;
import com.avg.lawsuitmanagement.member.repository.MemberMapperRepository;
import com.avg.lawsuitmanagement.promotion.dto.ClientPromotionKeyDto;
import com.avg.lawsuitmanagement.promotion.dto.ClientPromotionMailDto;
import com.avg.lawsuitmanagement.promotion.dto.EmployeePromotionKeyDto;
import com.avg.lawsuitmanagement.promotion.dto.EmployeePromotionMailDto;
import com.avg.lawsuitmanagement.promotion.repository.PromotionMapperRepository;
import com.avg.lawsuitmanagement.promotion.repository.param.InsertClientPromotionKeyParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PromotionService {

    private final PromotionMapperRepository promotionMapperRepository;
    private final MemberMapperRepository memberMapperRepository;
    private final ClientService clientService;
    private final PromotionMailService promotionMailService;

    @Transactional
    public String createClientPromotionKey(long clientId, boolean isSendMail) {
        String promotionKey = getRandomPromotionKey();

        //존재하는 유저인지? -> ClientService에서 검증
        //이미 가입된 의뢰인인지?
        ClientDto clientDto = clientService.getClientById(clientId);
        if (clientDto.getMemberId() != 0) {
            throw new CustomRuntimeException(ErrorCode.CLIENT_ALREADY_REGISTERED);
        }

        //db 입력
        promotionMapperRepository.insertClientPromotionKey(InsertClientPromotionKeyParam.builder()
            .value(promotionKey)
            .clientId(clientDto.getId())
            .build());
        if (isSendMail) {
            sendClientPromotionMail(clientDto, promotionKey);
        }
        //return
        return promotionKey;
    }

    public ClientDto resolveClientPromotionKey(String key) {
        ClientPromotionKeyDto clientPromotionKeyDto = promotionMapperRepository.selectPromotionKeyByValue(
            key);
        validatePromotionKey(clientPromotionKeyDto);
        return clientService.getClientById(clientPromotionKeyDto.getClientId());
    }

    public String createEmployeePromotionKey(String email, boolean isSendMail) {
        String promotionKey = getRandomPromotionKey();

        if(isSendMail) {
            sendEmployeePromotionService(email, promotionKey);
        }

        promotionMapperRepository.insertEmployeePromotionKey(promotionKey);
        return promotionKey;
    }

    public void validateEmployeePromotionKey(String key) {
        EmployeePromotionKeyDto employeePromotionKeyDto = promotionMapperRepository.selectEmployeePromotionKeyByValue(
            key);
        validatePromotionKey(employeePromotionKeyDto);
    }

    public void deactivateClientPromotion(String key) {
        promotionMapperRepository.deactivateClientPromotionByValue(key);
    }

    public void deactivateEmployeePromotion(String key) {
        promotionMapperRepository.deactivateEmployeePromotionByValue(key);
    }

    /*
    프로모션 키 검증
     */
    private void validatePromotionKey(ClientPromotionKeyDto dto) {
        if (dto == null) {
            throw new CustomRuntimeException(PROMOTION_NOT_FOUND);
        }
        if (!dto.isActive()) {
            throw new CustomRuntimeException(PROMOTION_NOT_ACTIVE);
        }
    }

    private void validatePromotionKey(EmployeePromotionKeyDto dto) {
        if (dto == null) {
            throw new CustomRuntimeException(PROMOTION_NOT_FOUND);
        }
        if (!dto.isActive()) {
            throw new CustomRuntimeException(PROMOTION_NOT_ACTIVE);
        }
    }

    //메일
    private void sendClientPromotionMail(ClientDto clientDto, String promotionKey) {
        //메일 전송
        String issuer = memberMapperRepository.selectMemberByEmail(
            SecurityUtil.getCurrentLoginEmail()).getName();

        promotionMailService.sendClientPromotionMail(ClientPromotionMailDto.builder()
            .to(clientDto.getEmail())
            .clientName(clientDto.getName())
            .clientPhone(clientDto.getPhone())
            .promotionKey(promotionKey)
            .issuer(issuer)
            .build());
    }

    private void sendEmployeePromotionService(String emailTo, String promotionKey) {
        //메일 전송
        String issuer = memberMapperRepository.selectMemberByEmail(
            SecurityUtil.getCurrentLoginEmail()).getName();

        promotionMailService.sendEmployeePromotionMail(EmployeePromotionMailDto.builder()
            .to(emailTo)
            .promotionKey(promotionKey)
            .issuer(issuer)
            .build());
    }

    private String getRandomPromotionKey() {
        return RandomStringUtils.randomAlphabetic(10);
    }
}
