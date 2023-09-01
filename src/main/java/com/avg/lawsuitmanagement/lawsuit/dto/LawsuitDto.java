package com.avg.lawsuitmanagement.lawsuit.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class LawsuitDto {
    private Long id;
    private String lawsuitType;
    private String name;
    private int courtId;
    private Long commissionFee;
    private Long contingentFee;
    private String lawsuitStatus;
    private String lawsuitNum;
    private String result;
    private String judgementDate;
    private String createdAt;
    private String updatedAt;
}
