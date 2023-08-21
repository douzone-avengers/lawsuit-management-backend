package com.avg.lawsuitmanagement.schedule.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
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
public class ScheduleDto {

    private Long receptionId;
    private Boolean receptionStatus;
    private LocalDate deadline;
    private Long lawsuitId;
    private String lawsuitType;
    private String lawsuitName;
    private List<String> employeeNames;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ScheduleDto that = (ScheduleDto) o;
        return Objects.equals(receptionId, that.receptionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(receptionId);
    }
}
