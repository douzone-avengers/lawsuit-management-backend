package com.avg.lawsuitmanagement.advice.repository.param;

import com.avg.lawsuitmanagement.advice.controller.form.InsertAdviceForm;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class InsertAdviceParam {


    private String title; //상담 제목
    private String contents; //상담 내용
    private String advicedAt; // 상담 일시
    private long lawsuitId;

    public static InsertAdviceParam of(InsertAdviceForm form){
        return InsertAdviceParam.builder()
                .title(form.getTitle())
                .contents(form.getContents())
                .advicedAt(form.getAdvicedAt())
                .lawsuitId(form.getLawsuitId())
                .build();

    }


}
