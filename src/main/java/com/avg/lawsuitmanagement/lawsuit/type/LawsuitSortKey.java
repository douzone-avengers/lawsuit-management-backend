package com.avg.lawsuitmanagement.lawsuit.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LawsuitSortKey {
    CREATED_AT("등록일", "created_at"),
    NAME("사건명", "name"),
    LAWSUIT_NUM("사건번호", "lawsuit_num"),
    LAWSUIT_STATUS("사건상태", "lawsuit_status"),
    COMMISSION_FEE("의뢰비", "commission_fee"),
    CONTINGENT_FEE("성공보수", "contingent_fee");

    private final String nameKr;
    private final String queryString;
}
