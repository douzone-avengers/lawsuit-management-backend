package com.avg.lawsuitmanagement.expense.repository.param;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class InsertExpenseFileIdParam {
    private Long expenseId;
    private Long fileId;

    public static InsertExpenseFileIdParam of(long expenseId, long fileId) {
        return InsertExpenseFileIdParam.builder()
                .expenseId(expenseId)
                .fileId(fileId)
                .build();
    }
}
