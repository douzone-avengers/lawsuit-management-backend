package com.avg.lawsuitmanagement.lawsuit.repository.param;

import com.avg.lawsuitmanagement.lawsuit.controller.form.InsertLawsuitForm;
import com.avg.lawsuitmanagement.lawsuit.type.LawsuitStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class InsertLawsuitParam {
    private String lawsuitType;
    private String name;
    private int courtId;   // 담당법원 -> int로 받을지 string으로 받을지?
    private Long commissionFee; // 의뢰비
    private Long contingentFee; // 성공보수비용
    private String lawsuitNum;
    private String lawsuitStatus;

    public static InsertLawsuitParam of(InsertLawsuitForm form, LawsuitStatus status) {
        return InsertLawsuitParam.builder()
            .lawsuitType(form.getLawsuitType())
            .name(form.getName())
            .courtId(form.getCourtId())
            .commissionFee(form.getCommissionFee())
            .contingentFee(form.getContingentFee())
            .lawsuitNum(form.getLawsuitNum())
            .lawsuitStatus(status.name())
            .build();
    }
}
