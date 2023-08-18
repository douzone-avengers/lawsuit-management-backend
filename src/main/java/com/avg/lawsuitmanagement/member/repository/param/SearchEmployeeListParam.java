package com.avg.lawsuitmanagement.member.repository.param;

import com.avg.lawsuitmanagement.common.util.dto.PagingDto;
import com.avg.lawsuitmanagement.member.controller.form.SearchEmployeeListForm;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SearchEmployeeListParam {

    //검색 및 정렬
    private String searchWord;
    private long hierarchyId;
    private long roleId;
    private String sortKey;
    private String sortOrder;

    //페이징
    private Integer offset;
    private Integer limit;

    public static SearchEmployeeListParam of(SearchEmployeeListForm form) {
        return SearchEmployeeListParam.builder()
            .searchWord(form.getSearchWord())
            .hierarchyId(form.getHierarchyId())
            .roleId(form.getRoleId())
            .sortKey(
                form.getSortKey() != null ? form.getSortKey().getQueryString() : null)
            .sortOrder(
                form.getSortOrder() != null ? form.getSortOrder().getQueryString()
                    : null)
            .build();
    }

    public static SearchEmployeeListParam of(SearchEmployeeListForm form, PagingDto pagingDto) {
        return SearchEmployeeListParam.builder()
            .searchWord(form.getSearchWord())
            .hierarchyId(form.getHierarchyId())
            .roleId(form.getRoleId())
            .sortKey(
                form.getSortKey() != null ? form.getSortKey().getQueryString() : null)
            .sortOrder(
                form.getSortOrder() != null ? form.getSortOrder().getQueryString()
                    : null)
            .offset(pagingDto.getOffset())
            .limit(pagingDto.getLimit())
            .build();
    }
}