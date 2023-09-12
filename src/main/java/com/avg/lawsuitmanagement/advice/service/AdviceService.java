package com.avg.lawsuitmanagement.advice.service;

import com.avg.lawsuitmanagement.advice.controller.form.InsertAdviceForm;
import com.avg.lawsuitmanagement.advice.controller.form.UpdateAdviceInfoForm;
import com.avg.lawsuitmanagement.advice.dto.AdviceDto;
import com.avg.lawsuitmanagement.advice.dto.IdNameDto;
import com.avg.lawsuitmanagement.advice.repository.AdviceMapperRepository;
import com.avg.lawsuitmanagement.advice.repository.param.InsertAdviceClientIdParam;
import com.avg.lawsuitmanagement.advice.repository.param.InsertAdviceMemberIdParam;
import com.avg.lawsuitmanagement.advice.repository.param.InsertAdviceParam;
import com.avg.lawsuitmanagement.advice.repository.param.UpdateAdviceInfoParam;
import com.avg.lawsuitmanagement.client.repository.ClientMapperRepository;
import com.avg.lawsuitmanagement.common.custom.CustomRuntimeException;
import com.avg.lawsuitmanagement.common.util.SecurityUtil;
import com.avg.lawsuitmanagement.lawsuit.dto.LawsuitDto;
import com.avg.lawsuitmanagement.lawsuit.repository.LawsuitMapperRepository;
import com.avg.lawsuitmanagement.member.dto.MemberDto;
import com.avg.lawsuitmanagement.member.repository.MemberMapperRepository;
import com.avg.lawsuitmanagement.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

import static com.avg.lawsuitmanagement.common.exception.type.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdviceService {

    private final AdviceMapperRepository adviceMapperRepository;
    private final LawsuitMapperRepository lawsuitMapperRepository;
    private final MemberMapperRepository memberMapperRepository;
    private final MemberService memberService;
    private final ClientMapperRepository clientMapperRepository;

    public AdviceDto getAdviceById(long adviceId) {
        AdviceDto adviceDto = adviceMapperRepository.selectAdviceById(adviceId);
        if(adviceDto == null) {
            throw new CustomRuntimeException(ADVICE_NOT_FOUND);
        }
        return adviceDto;
    }

    public List<AdviceDto> getAdviceByLawsuitId(long lawsuitId) {
        List<AdviceDto> adviceList = adviceMapperRepository.selectAdviceByLawsuitId(lawsuitId);
        if(adviceList == null){
            throw new CustomRuntimeException(ADVICE_NOT_FOUND);
        }
        return adviceList;
    }
    public void insertAdvice(InsertAdviceForm form) {
        // 소송 정보를 확인
        LawsuitDto lawsuitDto = lawsuitMapperRepository.selectLawsuitById(form.getLawsuitId());
        if (lawsuitDto == null) {
            throw new CustomRuntimeException(LAWSUIT_NOT_FOUND);
        }

        // 클라이언트와 멤버 ID 검색
        List<Long> clientIdList = clientMapperRepository.selectClientByLawsuitId(form.getLawsuitId());
        List<Long> memberIdList = memberService.selectMemberIdListByLawsuitId(form.getLawsuitId());

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

        List<Long> clientIdList = clientMapperRepository.selectClientByLawsuitId(form.getLawsuitId());
        List<Long> memberIdList = memberMapperRepository.selectMemberIdListByLawsuitId(form.getLawsuitId());

        @NotNull
        List<IdNameDto> formClientIdList = form.getClientIdList();
        List<Long> insertClientIdList = new ArrayList<>();

        for(IdNameDto clientId : formClientIdList){
            if(!clientIdList.contains(clientId)){
                throw new CustomRuntimeException(CLIENT_NOT_FOUND_IN_LAWSUIT);
            }
            insertClientIdList.add(clientId.getId());
        }
        InsertAdviceClientIdParam clientIdParam = InsertAdviceClientIdParam.of(adviceId, insertClientIdList);
        adviceMapperRepository.insertAdviceClientMap(clientIdParam);
        @NotNull
        List<IdNameDto> formMemberIdList = form.getMemberIdList();
        List<Long> insertMemberIdList = new ArrayList<>();
        for(IdNameDto memberId : formMemberIdList){
            if(!memberIdList.contains(memberId)){
                throw new CustomRuntimeException(MEMBER_NOT_ASSIGNED_TO_LAWSUIT);
            }
            insertMemberIdList.add(memberId.getId());
        }
        InsertAdviceMemberIdParam memberIdParam = InsertAdviceMemberIdParam.of(adviceId, insertMemberIdList);
        adviceMapperRepository.insertAdviceMemberMap(memberIdParam);
        adviceMapperRepository.updateAdviceInfo(UpdateAdviceInfoParam.of(adviceId, form));
    }

    @Transactional
    public void deleteAdviceInfo(long adviceId){
        AdviceDto adviceDto = adviceMapperRepository.selectAdviceById(adviceId);

        if(adviceDto == null){
            throw new CustomRuntimeException(ADVICE_NOT_FOUND);
        }
        String email = SecurityUtil.getCurrentLoginEmail();
        MemberDto loginMemberInfo = memberMapperRepository.selectMemberByEmail(email);

        List<Long> memberIdList = adviceMapperRepository.selectMemberByAdviceId(adviceId);
        if (!memberIdList.contains((loginMemberInfo.getId()))){
            throw new CustomRuntimeException(MEMBER_NOT_ASSIGNED_TO_ADVICE);
        }

        adviceMapperRepository.deleteAdviceInfo(adviceId);
        adviceMapperRepository.deleteAdviceClientMap(adviceId);
        adviceMapperRepository.deleteAdviceMemberMap(adviceId);
    }
}
