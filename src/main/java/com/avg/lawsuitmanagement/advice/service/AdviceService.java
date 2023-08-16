package com.avg.lawsuitmanagement.advice.service;

import com.avg.lawsuitmanagement.advice.controller.form.InsertAdviceForm;
import com.avg.lawsuitmanagement.advice.controller.form.UpdateAdviceInfoForm;
import com.avg.lawsuitmanagement.advice.dto.AdviceDto;
import com.avg.lawsuitmanagement.advice.repository.AdviceMapperRepository;
import com.avg.lawsuitmanagement.advice.repository.param.*;
import com.avg.lawsuitmanagement.common.custom.CustomRuntimeException;
import com.avg.lawsuitmanagement.lawsuit.dto.LawsuitDto;
import com.avg.lawsuitmanagement.lawsuit.repository.LawsuitMapperRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.avg.lawsuitmanagement.common.exception.type.ErrorCode.*;

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

    public void insertAdvice(InsertAdviceForm form) {
        // 소송 정보를 확인
        LawsuitDto lawsuitDto = lawsuitMapperRepository.selectLawsuitById(form.getLawsuitId());
        if (lawsuitDto == null) {
            throw new CustomRuntimeException(LAWSUIT_NOT_FOUND);
        }

        // 클라이언트와 멤버 ID 검색
        List<Long> clientIdList = lawsuitMapperRepository.selectClientByLawsuitId(form.getLawsuitId());
        List<Long> memberIdList = lawsuitMapperRepository.selectMemberByLawsuitId(form.getLawsuitId());

        // Advice 정보를 삽입
        adviceMapperRepository.insertAdvice(InsertAdviceParam.of(form));

        // Advice ID를 가져옴
        Long adviceId = adviceMapperRepository.getLastInsertedAdviceId();

        // advice_client_map 테이블에 데이터 삽입
        List<Long> formClientIdList = form.getClientIdList();
        List<Long> insertClientIdList = new ArrayList<>();

        for(Long clientId : formClientIdList){
            if(!clientIdList.contains(clientId)){
                throw new CustomRuntimeException(CLIENT_NOT_FOUND_IN_LAWSUIT);
            }
            insertClientIdList.add(clientId);
        }
        InsertAdviceClientIdParam clientIdParam = InsertAdviceClientIdParam.of(adviceId, insertClientIdList);
        adviceMapperRepository.insertAdviceClientMap(clientIdParam);



        // advice_member_map 테이블에 데이터 삽입
       List<Long> formMemberIdList = form.getMemberIdList();
        List<Long> insertMemberIdList = new ArrayList<>();
        for(Long memberId : formMemberIdList){
            if(!memberIdList.contains(memberId)){
                throw new CustomRuntimeException(MEMBER_NOT_ASSIGNED_TO_LAWSUIT);
            }
            insertMemberIdList.add(memberId);
        }
        InsertAdviceMemberIdParam memberIdParam = InsertAdviceMemberIdParam.of(adviceId, insertMemberIdList);

        adviceMapperRepository.insertAdviceMemberMap(memberIdParam);
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
