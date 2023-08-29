package com.avg.lawsuitmanagement.member.repository.param;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DeleteEmployeeFromLawsuitParam {

    private long employeeId;
    private long lawsuitId;
}