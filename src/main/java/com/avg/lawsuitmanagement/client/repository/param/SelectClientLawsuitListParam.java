package com.avg.lawsuitmanagement.client.repository.param;

import com.avg.lawsuitmanagement.common.util.dto.PagingDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SelectClientLawsuitListParam {
    private long clientId;
    private int offset;
    private int limit;
    private String searchWord;


    public static SelectClientLawsuitListParam of(long clientId, PagingDto pagingDto, String searchWord) {
        return SelectClientLawsuitListParam.builder()
            .clientId(clientId)
            .offset(pagingDto.getOffset())
            .limit(pagingDto.getLimit())
            .searchWord(searchWord)
            .build();
    }
}
