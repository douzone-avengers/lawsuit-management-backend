package com.avg.lawsuitmanagement.expense.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExpenseSortKey {
    SPENING_AT("지출일", "spening_at"),
    CONTENTS("내용", "contents"),
    AMOUNT("금액", "amount");

    private final String nameKr;
    private final String queryString;
}
