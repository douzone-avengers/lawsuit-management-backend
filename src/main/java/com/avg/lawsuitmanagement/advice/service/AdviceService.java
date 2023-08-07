package com.avg.lawsuitmanagement.advice.service;

import com.avg.lawsuitmanagement.advice.controller.form.InsertAdviceForm;
import com.avg.lawsuitmanagement.advice.dto.AdviceDto;
import com.avg.lawsuitmanagement.advice.repository.AdviceMapperRepository;
import com.avg.lawsuitmanagement.advice.repository.param.InsertAdviceParam;

import com.avg.lawsuitmanagement.common.custom.CustomRuntimeException;
import com.avg.lawsuitmanagement.lawsuit.dto.LawsuitDto;
import com.avg.lawsuitmanagement.lawsuit.repository.LawsuitMapperRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    public void insertAdvice(InsertAdviceForm form){
        LawsuitDto lawsuitDto = lawsuitMapperRepository.selectLawsuitById(form.getLawsuitId());

        if(lawsuitDto == null) {
            throw new CustomRuntimeException(LAWSUIT_NOT_FOUND);
        }



        adviceMapperRepository.insertAdvice(InsertAdviceParam.of(form));

    }
}
