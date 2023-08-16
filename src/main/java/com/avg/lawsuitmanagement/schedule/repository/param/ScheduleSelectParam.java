package com.avg.lawsuitmanagement.schedule.repository.param;

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
public class ScheduleSelectParam {

    private Long memberId;
    private LocalDate startDeadline;
    private LocalDate endDeadline;

}
