package com.avg.lawsuitmanagement.statistics.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class RevenueDto {
    private long total;
    private long commissionFee;
    private long contingentFee;

}
