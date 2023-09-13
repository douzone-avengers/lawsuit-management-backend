package com.avg.lawsuitmanagement.lawsuit.repository.param;

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
public class LawsuitUpdateResultParam {

    private Long lawsuitId;
    private String result;

}
