package com.avg.lawsuitmanagement.expense.repository.param;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ExpenseBillSelectParam {
    private Long expenseId;
    private Long page;
    private Long count;

    public static ExpenseBillSelectParam of (Long expenseId, Long page, Long count) {
        return ExpenseBillSelectParam.builder()
                .expenseId(expenseId)
                .page(page)
                .count(count)
                .build();
    }
}
