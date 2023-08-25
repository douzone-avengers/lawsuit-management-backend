package com.avg.lawsuitmanagement.lawsuit.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class LawsuitCountDto {
    private int total;
    private int registration;
    private int proceeding;
    private int closing;
}
