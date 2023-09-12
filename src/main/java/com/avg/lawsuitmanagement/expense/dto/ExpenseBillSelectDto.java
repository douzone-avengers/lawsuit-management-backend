package com.avg.lawsuitmanagement.expense.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ExpenseBillSelectDto {
    private Long expenseId;
    private Long page;
    private Long count;

    public static ExpenseBillSelectDto of (Long expenseId, Long page, Long count) {
        return ExpenseBillSelectDto.builder()
                .expenseId(expenseId)
                .page(page)
                .count(count)
                .build();
    }
}
