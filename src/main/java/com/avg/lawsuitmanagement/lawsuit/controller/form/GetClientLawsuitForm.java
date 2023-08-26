package com.avg.lawsuitmanagement.lawsuit.controller.form;

import com.avg.lawsuitmanagement.common.type.SortOrder;
import com.avg.lawsuitmanagement.lawsuit.type.LawsuitStatus;
import com.avg.lawsuitmanagement.lawsuit.type.LawsuitSortKey;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GetClientLawsuitForm {
    // 검색 및 정렬
    private String searchWord;
    private LawsuitStatus lawsuitStatus;
    private LawsuitSortKey sortKey;
    private SortOrder sortOrder;

    // 페이징
    private Integer curPage;
    private Integer rowsPerPage;
}
