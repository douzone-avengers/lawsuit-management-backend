package com.avg.lawsuitmanagement.expense.repository.param;

import com.avg.lawsuitmanagement.expense.controller.form.ExpenseUpdateForm;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ExpenseUpdateParam {
    private Long expenseId;
    private LocalDate speningAt;
    private String contents;
    private Long amount;

    public static ExpenseUpdateParam of (Long expenseId, ExpenseUpdateForm form) {
        return ExpenseUpdateParam.builder()
            .expenseId(expenseId)
            .speningAt(form.getSpeningAt())
            .contents(form.getContents())
            .amount(form.getAmount())
            .build();
    }
}
