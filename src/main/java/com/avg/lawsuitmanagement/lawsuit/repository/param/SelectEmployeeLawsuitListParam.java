package com.avg.lawsuitmanagement.lawsuit.repository.param;

import com.avg.lawsuitmanagement.common.util.dto.PagingDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SelectEmployeeLawsuitListParam {
    private long memberId;
    private int offset;
    private int limit;
    private String searchWord;

    public static SelectEmployeeLawsuitListParam of(long employeeId, PagingDto pagingDto, String searchWord) {
        return SelectEmployeeLawsuitListParam.builder()
            .memberId(employeeId)
            .offset(pagingDto.getOffset())
            .limit(pagingDto.getLimit())
            .searchWord(searchWord)
            .build();
    }
}
