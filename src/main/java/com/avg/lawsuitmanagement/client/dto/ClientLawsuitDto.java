package com.avg.lawsuitmanagement.client.dto;

import com.avg.lawsuitmanagement.common.util.dto.PageRangeDto;
import com.avg.lawsuitmanagement.lawsuit.dto.LawsuitDto;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ClientLawsuitDto {
    private List<LawsuitDto> lawsuitList;
    private PageRangeDto pageRange;

     public static ClientLawsuitDto of (List<LawsuitDto> lawsuitList, PageRangeDto pageRange) {
         return ClientLawsuitDto.builder()
            .lawsuitList(lawsuitList)
            .pageRange(pageRange)
            .build();
    }
}
