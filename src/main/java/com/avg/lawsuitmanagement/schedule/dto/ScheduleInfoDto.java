package com.avg.lawsuitmanagement.schedule.dto;

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
public class ScheduleInfoDto {

    private ScheduleLawsuitInfoDto lawsuit;
    private List<ScheduleMemberInfoDto> members;
    private List<ScheduleClientInfoDto> clients;
    private ScheduleReceptionInfoDto reception;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ScheduleInfoDto that = (ScheduleInfoDto) o;
        return Objects.equals(reception.getId(), that.reception.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(reception.getId());
    }
}
