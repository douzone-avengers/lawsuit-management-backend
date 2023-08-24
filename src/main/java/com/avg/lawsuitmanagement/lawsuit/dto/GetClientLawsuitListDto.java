package com.avg.lawsuitmanagement.lawsuit.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GetClientLawsuitListDto {
    private List<LawsuitDto> lawsuitList;
    private LawsuitCountDto countDto;
}
