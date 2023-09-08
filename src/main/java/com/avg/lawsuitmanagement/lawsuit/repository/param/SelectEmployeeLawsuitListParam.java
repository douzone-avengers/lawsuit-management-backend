package com.avg.lawsuitmanagement.lawsuit.repository.param;

import com.avg.lawsuitmanagement.common.type.SortOrder;
import com.avg.lawsuitmanagement.common.util.dto.PagingDto;
import com.avg.lawsuitmanagement.lawsuit.controller.form.GetEmployeeLawsuitForm;
import com.avg.lawsuitmanagement.lawsuit.type.LawsuitSortKey;
import com.avg.lawsuitmanagement.lawsuit.type.LawsuitStatus;
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
    private LawsuitStatus lawsuitStatus;
    private String sortKey;
    private String sortOrder;


    public static SelectEmployeeLawsuitListParam of(long employeeId, PagingDto pagingDto, GetEmployeeLawsuitForm form) {
        LawsuitSortKey sortKey = form.getSortKey();
        SortOrder sortOrder = form.getSortOrder();
        return SelectEmployeeLawsuitListParam.builder()
            .memberId(employeeId)
            .offset(pagingDto.getOffset())
            .limit(pagingDto.getLimit())
            .searchWord(form.getSearchWord())
            .lawsuitStatus(form.getLawsuitStatus())
            .sortKey(
                sortKey != null ? sortKey.getQueryString() : null)
            .sortOrder(
                sortOrder != null ? sortOrder.getQueryString()
                    : null)
            .build();
    }
    public static SelectEmployeeLawsuitListParam of(long employeeId, String searchWord, LawsuitStatus lawsuitStatus) {
        return SelectEmployeeLawsuitListParam.builder()
            .memberId(employeeId)
            .searchWord(searchWord)
            .lawsuitStatus(lawsuitStatus)
            .offset(0)
            .limit(0)
            .build();
    }
}
