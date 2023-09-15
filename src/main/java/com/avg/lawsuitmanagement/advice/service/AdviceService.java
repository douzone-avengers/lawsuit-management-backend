package com.avg.lawsuitmanagement.advice.service;

import com.avg.lawsuitmanagement.advice.controller.form.InsertAdviceForm;
import com.avg.lawsuitmanagement.advice.controller.form.UpdateAdviceInfoForm;
import com.avg.lawsuitmanagement.advice.dto.*;
import com.avg.lawsuitmanagement.advice.repository.AdviceMapperRepository;
import com.avg.lawsuitmanagement.advice.repository.param.*;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public AdviceDetailResponseDto getAdviceInfo(Long adviceId) {
        List<AdviceRawDto> raws = adviceMapperRepository.detailAdviceById(adviceId);

        if(raws.isEmpty()){
            throw new RuntimeException("");
        }
        AdviceRawDto advice = raws.get(0);

        Map<Long, MemberIdNameDto> memberMap = new HashMap<>();
        Map<Long, ClientIdNameDto> clientMap = new HashMap<>();
        for(AdviceRawDto raw : raws) {
            Long memberId = raw.getMemberId();
            if(memberId != null) {
                if(!memberMap.containsKey(memberId)) {
                    memberMap.put(memberId, MemberIdNameDto.builder()
                            .id(raw.getMemberId())
                            .name(raw.getMemberName())
                            .build());
                }
            }

            Long clientId = raw.getClientId();
            if(clientId != null) {
                if(!clientMap.containsKey(clientId)){
                    clientMap.put(clientId, ClientIdNameDto.builder()
                            .id(raw.getClientId())
                            .name(raw.getClientName())
                            .build());
                }
            }

        }
        AdviceDetailResponseDto result = AdviceDetailResponseDto.builder()
                .adviceId(advice.getAdviceid())
                .title(advice.getTitle())
                .contents(advice.getContents())
                .advicedAt(advice.getAdvicedAt())
                .clients(clientMap.values().stream().map(it -> ClientIdNameDto.builder()
                        .id(it.getId())
                        .name(it.getName())
                        .build())
                        .toList())
                .members(memberMap.values().stream().map(it -> MemberIdNameDto.builder()
                        .id(it.getId())
                        .name(it.getName())
                        .build())
                        .toList())
                .build();

        return result;



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
        AdviceClientIdParam clientIdParam = AdviceClientIdParam.of(adviceId, insertClientIdList);
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
        AdviceMemberIdParam memberIdParam = AdviceMemberIdParam.of(adviceId, insertMemberIdList);

        adviceMapperRepository.insertAdviceMemberMap(memberIdParam);
    }



    @Transactional
    public void updateAdviceInfo(long adviceId, UpdateAdviceInfoForm form){
        List<AdviceRawDto> adviceRawDto = adviceMapperRepository.detailAdviceById(adviceId);
        if(adviceRawDto == null){
            throw new CustomRuntimeException(ADVICE_NOT_FOUND);
        }

        // 사건에 대한 담당자 당사자
        List<Long> clientIdList = clientMapperRepository.selectClientByLawsuitId(form.getLawsuitId());
        List<Long> memberIdList = memberMapperRepository.selectMemberIdListByLawsuitId(form.getLawsuitId());
        // 상담에 대한 담당자 당사자
        List<Long> adviceClientIdList = adviceMapperRepository.selectClientByAdviceId(adviceId);
        List<Long> adviceMemberIdList = adviceMapperRepository.selectMemberByAdviceId(adviceId);


        List<Long> formClientIdList = form.getClientIdList();
        List<Long> insertClientIdList = new ArrayList<>();
        List<Long> deleteClientIdList = new ArrayList<>();

        for(Long clientId : formClientIdList){
            if(!clientIdList.contains(clientId)){
                throw new CustomRuntimeException(CLIENT_NOT_FOUND_IN_LAWSUIT);
            }
            insertClientIdList.add(clientId);
        }

        for(Long clientId : adviceClientIdList){
            if(!form.getClientIdList().contains(clientId)){
                deleteClientIdList.add(clientId);
            }
        }



        AdviceClientIdParam clientIdParam = AdviceClientIdParam.of(adviceId, insertClientIdList);
        adviceMapperRepository.insertAdviceClientMap(clientIdParam);
        adviceMapperRepository.updateAdviceClientMap(clientIdParam);

        List<Long> formMemberIdList = form.getMemberIdList();
        List<Long> insertMemberIdList = new ArrayList<>();
        List<Long> deleteMemberIdList = new ArrayList<>();

        for(Long memberId : formMemberIdList){
            if(!memberIdList.contains(memberId)){
                throw new CustomRuntimeException(MEMBER_NOT_ASSIGNED_TO_LAWSUIT);
            }
            insertMemberIdList.add(memberId);
        }

        for(Long memberId : adviceMemberIdList){
            if(!form.getMemberIdList().contains(memberId)){
                deleteMemberIdList.add(memberId);
            }
        }

        AdviceMemberIdParam memberIdParam = AdviceMemberIdParam.of(adviceId, insertMemberIdList);
        adviceMapperRepository.insertAdviceMemberMap(memberIdParam);
        adviceMapperRepository.updateAdviceMemberMap(memberIdParam);
        DeleteAdviceClientMemberIdParam DeleteIdParam = DeleteAdviceClientMemberIdParam.of(adviceId, deleteClientIdList,deleteMemberIdList);

        adviceMapperRepository.deleteAdviceMemberMap(DeleteIdParam);
        adviceMapperRepository.deleteAdviceClientMap(DeleteIdParam);
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

    }
}
