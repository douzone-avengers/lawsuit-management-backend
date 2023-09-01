package com.avg.lawsuitmanagement.lawsuit.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class GetClientLawsuitListDto {
    private List<LawsuitDto> lawsuitList;
    private LawsuitCountDto countDto;
}
