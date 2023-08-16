package com.avg.lawsuitmanagement.client.dto;

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
    private int count;
}
