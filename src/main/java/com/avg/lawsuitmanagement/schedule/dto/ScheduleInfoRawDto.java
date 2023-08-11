package com.avg.lawsuitmanagement.schedule.dto;

import java.time.LocalDate;
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
public class ScheduleInfoRawDto {

    private Long lawsuitId;
    private String lawsuitNum;
    private String lawsuitName;
    private String lawsuitType;
    private Long lawsuitCommissionFee;
    private Long lawsuitContingentFee;
    private Long memberId;
    private String memberName;
    private Long clientId;
    private String clientName;
    private Long receptionId;
    private Boolean receptionStatus;
    private String receptionCategory;
    private String receptionContents;
    private LocalDate receptionReceivedAt;
    private LocalDate receptionDeadline;

}
