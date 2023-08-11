package com.avg.lawsuitmanagement.advice.service;

import com.avg.lawsuitmanagement.advice.controller.form.InsertAdviceForm;
import com.avg.lawsuitmanagement.advice.controller.form.UpdateAdviceInfoForm;
import com.avg.lawsuitmanagement.advice.dto.AdviceDto;
import com.avg.lawsuitmanagement.advice.repository.AdviceMapperRepository;
import com.avg.lawsuitmanagement.advice.repository.param.InsertAdviceClientMemberIdParam;
import com.avg.lawsuitmanagement.advice.repository.param.InsertAdviceParam;
import com.avg.lawsuitmanagement.advice.repository.param.UpdateAdviceInfoParam;
import com.avg.lawsuitmanagement.common.custom.CustomRuntimeException;
import com.avg.lawsuitmanagement.lawsuit.dto.LawsuitDto;
import com.avg.lawsuitmanagement.lawsuit.repository.LawsuitMapperRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import static com.avg.lawsuitmanagement.common.exception.type.ErrorCode.ADVICE_NOT_FOUND;
import static com.avg.lawsuitmanagement.common.exception.type.ErrorCode.LAWSUIT_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdviceService {

    private final AdviceMapperRepository adviceMapperRepository;
    private final LawsuitMapperRepository lawsuitMapperRepository;

    public AdviceDto getAdviceById(long adviceId) {
        AdviceDto adviceDto = adviceMapperRepository.selectAdviceById(adviceId);

        if(adviceDto == null) {
            throw new CustomRuntimeException(ADVICE_NOT_FOUND);
        }

        return adviceDto;
    }

    public void insertAdvice(long lawsuitId, InsertAdviceForm form) {
        // 소송 정보를 확인
        LawsuitDto lawsuitDto = lawsuitMapperRepository.selectLawsuitById(lawsuitId);
        if (lawsuitDto == null) {
            throw new CustomRuntimeException(LAWSUIT_NOT_FOUND);
        }

        // 클라이언트와 멤버 ID 검색
        List<Long> clientIdList = lawsuitMapperRepository.selectClientByLawsuitId(lawsuitId);
        List<Long> memberIdList = lawsuitMapperRepository.selectMemberByLawsuitId(lawsuitId);

        // Advice 정보를 삽입
        adviceMapperRepository.insertAdvice(InsertAdviceParam.of(form));

        // Advice ID를 가져옴
        Long adviceId = adviceMapperRepository.getLastInsertedAdviceId();

        // advice_client_map 테이블에 데이터 삽입
        List<Long> filteredClientIds = form.getClientIdList().stream()
                .filter(clientIdList::contains)
                .toList();

        for (Long clientId : filteredClientIds) {
            InsertAdviceClientMemberIdParam clientParam = InsertAdviceClientMemberIdParam.of(adviceId, Collections.singletonList(clientId), null);
            adviceMapperRepository.insertAdviceClientMap(clientParam);
        }

        // advice_member_map 테이블에 데이터 삽입
        List<Long> filteredMemberIds = form.getMemberIdList().stream()
                .filter(memberIdList::contains)
                .toList();

        for (Long memberId : filteredMemberIds) {
            InsertAdviceClientMemberIdParam memberParam = InsertAdviceClientMemberIdParam.of(adviceId, null, Collections.singletonList(memberId));
            adviceMapperRepository.insertAdviceMemberMap(memberParam);
        }
    }



    public void updateAdviceInfo(long adviceId, UpdateAdviceInfoForm form){
        AdviceDto adviceDto = adviceMapperRepository.selectAdviceById(adviceId);

        if(adviceDto == null){
            throw new CustomRuntimeException(ADVICE_NOT_FOUND);
        }
        adviceMapperRepository.updateAdviceInfo(UpdateAdviceInfoParam.of(adviceId, form));
    }

    public void deleteAdviceInfo(long adviceId){
        AdviceDto adviceDto = adviceMapperRepository.selectAdviceById(adviceId);

        if(adviceDto == null){
            throw new CustomRuntimeException(ADVICE_NOT_FOUND);
        }
        adviceMapperRepository.deleteAdviceInfo(adviceId);
    }
}
