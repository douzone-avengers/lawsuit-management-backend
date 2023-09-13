package com.avg.lawsuitmanagement.lawsuit.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class ClientLawsuitCountDto {
    private long lawsuitId;
    private int clientLawsuitCount;
}
