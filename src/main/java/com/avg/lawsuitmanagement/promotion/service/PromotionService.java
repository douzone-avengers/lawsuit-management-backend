package com.avg.lawsuitmanagement.promotion.service;

import static com.avg.lawsuitmanagement.common.exception.type.ErrorCode.PROMOTION_NOT_ACTIVE;
import static com.avg.lawsuitmanagement.common.exception.type.ErrorCode.PROMOTION_NOT_FOUND;

import com.avg.lawsuitmanagement.client.dto.ClientDto;
import com.avg.lawsuitmanagement.client.service.ClientService;
import com.avg.lawsuitmanagement.common.custom.CustomRuntimeException;
import com.avg.lawsuitmanagement.common.exception.type.ErrorCode;
import com.avg.lawsuitmanagement.promotion.dto.CreatePromotionKeyDto;
import com.avg.lawsuitmanagement.promotion.dto.PromotionKeyDto;
import com.avg.lawsuitmanagement.promotion.repository.PromotionMapperRepository;
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
    private final ClientService clientService;

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
        promotionMapperRepository.insertPromotionKey(promotionKey);

        //return
        return CreatePromotionKeyDto.builder()
            .value(promotionKey)
            .build();
    }

    public ClientDto resolveClientPromotionKey(String key) {
        PromotionKeyDto promotionKeyDto = promotionMapperRepository.selectPromotionKeyByValue(key);
        validatePromotionKey(promotionKeyDto);
        return clientService.getClientById(promotionKeyDto.getClientId());
    }

    /*
    프로모션 키 검증
     */
    private void validatePromotionKey(PromotionKeyDto dto) {
        if(dto == null) {
            throw new CustomRuntimeException(PROMOTION_NOT_FOUND);
        }
        if(!dto.isActive()) {
            throw new CustomRuntimeException(PROMOTION_NOT_ACTIVE);
        }
    }


    private String getRandomPromotionKey() {
        return RandomStringUtils.randomAlphabetic(10);
    }
}
