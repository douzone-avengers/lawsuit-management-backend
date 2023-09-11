package com.avg.lawsuitmanagement.expense.repository.param;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ExpenseFileIdListParam {
    private List<Long> expenseIdList;
    private List<Long> fileIdList;

    public static ExpenseFileIdListParam of (List<Long> expenseIdList, List<Long> fileIdList) {
        return ExpenseFileIdListParam.builder()
                .expenseIdList(expenseIdList)
                .fileIdList(fileIdList)
                .build();
    }
}
