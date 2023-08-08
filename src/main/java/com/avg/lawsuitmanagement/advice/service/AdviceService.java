package com.avg.lawsuitmanagement.advice.service;

import com.avg.lawsuitmanagement.advice.controller.form.InsertAdviceForm;
import com.avg.lawsuitmanagement.advice.controller.form.UpdateAdviceInfoForm;
import com.avg.lawsuitmanagement.advice.dto.AdviceDto;
import com.avg.lawsuitmanagement.advice.repository.AdviceMapperRepository;
import com.avg.lawsuitmanagement.advice.repository.param.InsertAdviceParam;

import com.avg.lawsuitmanagement.advice.repository.param.UpdateAdviceInfoParam;
import com.avg.lawsuitmanagement.client.dto.ClientDto;
import com.avg.lawsuitmanagement.client.repository.ClientMapperRepository;
import com.avg.lawsuitmanagement.common.custom.CustomRuntimeException;
import com.avg.lawsuitmanagement.lawsuit.dto.LawsuitDto;
import com.avg.lawsuitmanagement.lawsuit.repository.LawsuitMapperRepository;

import com.avg.lawsuitmanagement.member.dto.MemberDto;
import com.avg.lawsuitmanagement.member.repository.MemberMapperRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.avg.lawsuitmanagement.common.exception.type.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdviceService {

    private final AdviceMapperRepository adviceMapperRepository;
    private final LawsuitMapperRepository lawsuitMapperRepository;
    private final MemberMapperRepository memberMapperRepository;
    private final ClientMapperRepository clientMapperRepository;
    public AdviceDto getAdviceById(long adviceId) {
        AdviceDto adviceDto = adviceMapperRepository.selectAdviceById(adviceId);

        if(adviceDto == null) {
            throw new CustomRuntimeException(ADVICE_NOT_FOUND);
        }

        return adviceDto;
    }

    public void insertAdvice(InsertAdviceForm form){
        LawsuitDto lawsuitDto = lawsuitMapperRepository.selectLawsuitById(form.getLawsuitId());

        if(lawsuitDto == null) {
            throw new CustomRuntimeException(LAWSUIT_NOT_FOUND);
        }
        List<MemberDto> memberDto = memberMapperRepository.selectMemberListById(form.getMemberId());
        if(memberDto == null) {
            throw new CustomRuntimeException(MEMBER_NOT_FOUND);
        }
        List<ClientDto> clientDto = clientMapperRepository.selectClientListById(form.getClientId());
        if(clientDto == null ){
            throw new CustomRuntimeException(CLIENT_NOT_FOUND);
        }

        adviceMapperRepository.insertAdvice(InsertAdviceParam.of(form));

    }

    public void updateAdviceInfo(long adviceId, UpdateAdviceInfoForm form){
        AdviceDto adviceDto = adviceMapperRepository.selectAdviceById(adviceId);

        if(adviceDto == null){
            throw new CustomRuntimeException(ADVICE_NOT_FOUND);
        }
        adviceMapperRepository.updateAdvcieInfo(UpdateAdviceInfoParam.of(adviceId, form));
    }

    public void deleteAdviceInfo(long adviceId){
        AdviceDto adviceDto = adviceMapperRepository.selectAdviceById(adviceId);

        if(adviceDto == null){
            throw new CustomRuntimeException(ADVICE_NOT_FOUND);
        }
        adviceMapperRepository.deleteAdviceInfo(adviceId);
    }
}
