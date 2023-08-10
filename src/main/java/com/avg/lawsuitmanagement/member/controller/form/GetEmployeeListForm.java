package com.avg.lawsuitmanagement.member.controller.form;

import com.avg.lawsuitmanagement.member.type.MemberSortKey;
import com.avg.lawsuitmanagement.member.type.MemberSortOrder;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GetEmployeeListForm {

    //검색 및 정렬
    private String searchWord;
    private long hierarchyId;
    private long roleId;
    private MemberSortKey memberSortKey;
    private MemberSortOrder memberSortOrder;

    //페이징
    private int page;
    private int rowsPerPage;
}
