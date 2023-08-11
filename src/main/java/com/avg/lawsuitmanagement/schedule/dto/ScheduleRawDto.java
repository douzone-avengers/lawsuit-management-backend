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
public class ScheduleRawDto {

    private Long receptionId;
    private LocalDate deadline;
    private Long lawsuitId;
    private String lawsuitType;
    private String lawsuitName;
    private String employeeName;
    private Boolean receptionStatus;
    private String receptionCategory;
    private String receptionContents;
    private LocalDate receptionReceivedAt;
    private LocalDate receptionReceptionDeadline;

}
