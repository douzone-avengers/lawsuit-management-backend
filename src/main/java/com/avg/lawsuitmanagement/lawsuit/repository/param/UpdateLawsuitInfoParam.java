package com.avg.lawsuitmanagement.lawsuit.repository.param;

import com.avg.lawsuitmanagement.lawsuit.controller.form.UpdateLawsuitInfoForm;
import com.avg.lawsuitmanagement.lawsuit.type.LawsuitStatus;
import java.util.Date;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UpdateLawsuitInfoParam {
    private Long lawsuitId;
    private String lawsuitType;
    private String name;
    private int courtId;
    private Long commissionFee;
    private Long contingentFee;
    private String lawsuitStatus;
    private String lawsuitNum;
    private String result;
    private Date judgementDate;

    public static UpdateLawsuitInfoParam of(Long lawsuitId, UpdateLawsuitInfoForm form, LawsuitStatus status) {
        return UpdateLawsuitInfoParam.builder()
            .lawsuitId(lawsuitId)
            .lawsuitType(form.getLawsuitType())
            .name(form.getName())
            .courtId(form.getCourtId())
            .commissionFee(form.getCommissionFee())
            .contingentFee(form.getContingentFee())
            .lawsuitStatus(status.name())
            .lawsuitNum(form.getLawsuitNum())
            .result(form.getResult())
            .judgementDate(form.getJudgementDate())
            .build();
    }
}
