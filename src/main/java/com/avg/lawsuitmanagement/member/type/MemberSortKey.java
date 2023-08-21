package com.avg.lawsuitmanagement.member.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberSortKey {

    CREATED_AT("가입일", "created_at"),
    HIERARCHY("직급", "hierarchy_id"),
    ROLE("권한", "role_id"),
    EMAIL("이메일", "email"),
    NAME("이름", "name"),
    PHONE("전화번호", "phone");

    private final String nameKr;
    private final String queryString;
}
