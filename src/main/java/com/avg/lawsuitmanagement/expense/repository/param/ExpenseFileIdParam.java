package com.avg.lawsuitmanagement.expense.repository.param;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ExpenseFileIdParam {
    private Long expenseId;
    private Long fileId;

    public static ExpenseFileIdParam of(long expenseId, long fileId) {
        return ExpenseFileIdParam.builder()
                .expenseId(expenseId)
                .fileId(fileId)
                .build();
    }
}
