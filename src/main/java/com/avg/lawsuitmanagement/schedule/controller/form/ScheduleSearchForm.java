package com.avg.lawsuitmanagement.schedule.controller.form;

import com.avg.lawsuitmanagement.schedule.repository.param.ScheduleSelectParam;
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
public class ScheduleSearchForm {

    private LocalDate startDeadline;
    private LocalDate endDeadline;

    public ScheduleSelectParam toParam() {
        return ScheduleSelectParam.builder()
            .startDeadline(startDeadline)
            .endDeadline(endDeadline)
            .build();
    }
}
