package com.avg.lawsuitmanagement.lawsuit.dto;

import java.util.Date;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class LawsuitDto {
    private String lawsuitType;
    private String name;
    private int courtId;   // 담당법원 -> int로 받을지 string으로 받을지?
    private int commissionFee; // 의뢰비
    private int contingentFee; // 성공보수비용
    private String lawsuitStatus;
    private String lawsuitNum;
    private String result;
    private Date judgementDate;
    private Date createAt;
    private Date updatedAt;
    private boolean isDeleted;
}
