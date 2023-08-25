package com.avg.lawsuitmanagement.lawsuit.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LawsuitSortOrder {
    ASC("오름차순", "asc"),
    DESC("내림차순", "desc");

    private final String nameKr;
    private final String queryString;
}
