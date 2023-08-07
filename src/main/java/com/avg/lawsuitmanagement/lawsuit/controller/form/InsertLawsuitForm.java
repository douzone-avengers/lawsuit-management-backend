package com.avg.lawsuitmanagement.lawsuit.controller.form;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InsertLawsuitForm {
    private String lawsuitType;
    private String name;
    private int courtId;   // 담당법원 -> int로 받을지 string으로 받을지?
    private int commissionFee; // 의뢰비
    private int contingentFee; // 성공보수비용
    private String lawsuitNum;
    private List<Long> memberId;
    private List<Long> clientId;
}
