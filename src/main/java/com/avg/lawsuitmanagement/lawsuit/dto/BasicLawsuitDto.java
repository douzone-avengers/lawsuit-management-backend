package com.avg.lawsuitmanagement.lawsuit.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class BasicLawsuitDto {

    private Long lawsuitId;
    private String lawsuitNum;
    private String lawsuitName;
    private String lawsuitType;
    private Long lawsuitCommissionFee;
    private Long lawsuitContingentFee;
    private String lawsuitStatus;
    private String courtName;

}
