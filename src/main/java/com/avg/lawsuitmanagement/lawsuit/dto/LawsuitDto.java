package com.avg.lawsuitmanagement.lawsuit.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.RequiredArgsConstructor;

@Getter
@Setter
@RequiredArgsConstructor
public class LawsuitDto {
    private Long id;
    private String lawsuitType;
    private String name;
    private int courtId;
    private int commissionFee;
    private int contingentFee;
    private String lawsuitStatus;
    private String lawsuitNum;
    private String result;
    private String judgementDate;
    private String createdAt;
    private String updatedAt;
}
