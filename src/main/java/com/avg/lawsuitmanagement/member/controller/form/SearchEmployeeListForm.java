package com.avg.lawsuitmanagement.member.controller.form;

import com.avg.lawsuitmanagement.common.type.SortOrder;
import com.avg.lawsuitmanagement.member.type.MemberSortKey;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SearchEmployeeListForm {

    //검색 및 정렬
    private String searchWord;
    private Long hierarchyId;
    private Long roleId;
    private MemberSortKey sortKey;
    private SortOrder sortOrder;

    //페이징
    @NotNull
    private Integer page;
    @NotNull
    private Integer rowsPerPage;
}
