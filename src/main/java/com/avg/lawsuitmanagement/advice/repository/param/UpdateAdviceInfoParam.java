package com.avg.lawsuitmanagement.advice.repository.param;


import com.avg.lawsuitmanagement.advice.controller.form.UpdateAdviceInfoForm;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@Builder
public class UpdateAdviceInfoParam {

    private Long adviceId;
    private String title; //상담 제목
    private String contents; //상담 내용
    private String advicedAt; // 상담 일시
    private long lawsuitId;

    public static UpdateAdviceInfoParam of(Long adviceId, UpdateAdviceInfoForm form){
        return UpdateAdviceInfoParam.builder()
                .adviceId(adviceId)
                .title(form.getTitle())
                .contents(form.getContents())
                .advicedAt(form.getAdvicedAt())
                .build();

    }
}
