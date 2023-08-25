package com.avg.lawsuitmanagement.lawsuit.repository.param;

import com.avg.lawsuitmanagement.common.util.dto.PagingDto;
import com.avg.lawsuitmanagement.lawsuit.type.LawsuitSortKey;
import com.avg.lawsuitmanagement.lawsuit.type.LawsuitSortOrder;
import com.avg.lawsuitmanagement.lawsuit.type.LawsuitStatus;
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
    private LawsuitStatus lawsuitStatus;
    private String sortKey;
    private String sortOrder;

    public static SelectClientLawsuitListParam of(long clientId, PagingDto pagingDto, String searchWord, LawsuitStatus lawsuitStatus, LawsuitSortKey sortKey, LawsuitSortOrder sortOrder) {
        return SelectClientLawsuitListParam.builder()
            .clientId(clientId)
            .offset(pagingDto.getOffset())
            .limit(pagingDto.getLimit())
            .searchWord(searchWord)
            .lawsuitStatus(lawsuitStatus)
            .sortKey(
                sortKey != null ? sortKey.getQueryString() : null)
            .sortOrder(
                sortOrder != null ? sortOrder.getQueryString()
                    : null)
            .build();
    }

    public static SelectClientLawsuitListParam of(long clientId, String searchWord, LawsuitStatus lawsuitStatus) {
        return SelectClientLawsuitListParam.builder()
            .clientId(clientId)
            .searchWord(searchWord)
            .lawsuitStatus(lawsuitStatus)
            .offset(0)
            .limit(0)
            .build();
    }
}
