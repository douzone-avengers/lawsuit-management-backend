package com.avg.lawsuitmanagement.lawsuit.controller.form;

import com.avg.lawsuitmanagement.lawsuit.type.LawsuitStatus;
import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateLawsuitInfoForm {
    private String lawsuitType;
    private String name;
    private int courtId;
    private int commissionFee;
    private int contingentFee;
    private String lawsuitStatus;
    private String lawsuitNum;
    private String result;
    private Date judgementDate;
    private List<Long> memberId;
    private List<Long> clientId;
}
