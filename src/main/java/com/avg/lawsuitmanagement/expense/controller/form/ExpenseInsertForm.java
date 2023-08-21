package com.avg.lawsuitmanagement.expense.controller.form;

import com.avg.lawsuitmanagement.expense.repository.param.ExpenseInsertParam;
import java.time.LocalDate;
import javax.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ExpenseInsertForm {
    @Null
    private Long id;
    private Long lawsuitId;
    private String contents;
    private Long amount;
    private LocalDate speningAt;

    public ExpenseInsertParam toParam() {
        return ExpenseInsertParam.builder()
            .lawsuitId(lawsuitId)
            .contents(contents)
            .amount(amount)
            .speningAt(speningAt)
            .build();
    }
}
