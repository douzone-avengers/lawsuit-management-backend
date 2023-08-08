package com.avg.lawsuitmanagement.lawsuit.repository.param;

import com.avg.lawsuitmanagement.client.controller.form.UpdateClientInfoForm;
import com.avg.lawsuitmanagement.client.repository.param.UpdateClientInfoParam;
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
    private String lawsuit_type;
    private String name;
    private int court_id;
    private int commission_fee;
    private int contingent_fee;
    private String lawsuit_status;
    private String lawsuit_num;
    private String result;
    private Date judgement_date;

    public static UpdateLawsuitInfoParam of(Long lawsuitId, UpdateLawsuitInfoForm form, LawsuitStatus status) {
        return UpdateLawsuitInfoParam.builder()
            .lawsuitId(lawsuitId)
            .lawsuit_type(form.getLawsuit_type())
            .name(form.getName())
            .court_id(form.getCourt_id())
            .commission_fee(form.getCommission_fee())
            .contingent_fee(form.getContingent_fee())
            .lawsuit_status(status.name())
            .lawsuit_num(form.getLawsuit_num())
            .result(form.getResult())
            .judgement_date(form.getJudgement_date())
            .build();
    }
}
