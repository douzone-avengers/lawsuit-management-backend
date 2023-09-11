package com.avg.lawsuitmanagement.statistics.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class LawsuitStatusDto {
    private int total;
    private int registration;
    private int proceeding;
    private int closing;

}
