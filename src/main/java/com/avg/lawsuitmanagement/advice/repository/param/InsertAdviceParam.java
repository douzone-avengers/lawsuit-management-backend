package com.avg.lawsuitmanagement.advice.repository.param;

import com.avg.lawsuitmanagement.advice.controller.form.InsertAdviceForm;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class InsertAdviceParam {

    private long id;
    private String title; //상담 제목
    private String contents; //상담 내용
    private String adviceAt; // 상담 일시
    private List<Long> memberId;
    private List<Long> clientId;
    private long lawsuitId;

    public static InsertAdviceParam of(InsertAdviceForm form){
        return InsertAdviceParam.builder()
                .title(form.getTitle())
                .contents(form.getContnets())
                .adviceAt(form.getAdvicedAt())
                .memberId(form.getMemberId())
                .clientId(form.getClientId())
                .lawsuitId(form.getLawsuitId())
                .build();

    }


}
