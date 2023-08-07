package com.avg.lawsuitmanagement.lawsuit.controller.form;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateLawsuitInfoForm {
    private String lawsuit_type;
    private String name;
    private int court_id;
    private int commission_fee;
    private int contingent_fee;
    private String lawsuit_status;
    private String lawsuit_num;
    private String result;
    private Date judgement_date;
    private Date updated_at;
}
