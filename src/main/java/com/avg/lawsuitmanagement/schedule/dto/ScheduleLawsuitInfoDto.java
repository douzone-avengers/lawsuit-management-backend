package com.avg.lawsuitmanagement.schedule.dto;

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
public class ScheduleLawsuitInfoDto {

    private Long id;
    private String num;
    private String name;
    private String type;
    private String courtName;
    private String status;
    private Long commissionFee;
    private Long contingentFee;

}
