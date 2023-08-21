package com.avg.lawsuitmanagement.expense.controller.form;

import com.avg.lawsuitmanagement.expense.repository.param.ExpenseSelectParam;
import java.time.LocalDate;
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
public class ExpenseSearchForm {
    private Long lawsuitId;
    private Long page;
    private Long count;
    private String contents;
    private LocalDate startSpeningAt;
    private LocalDate endSpeningAt;
    private Long startAmount;
    private Long endAmount;

    public ExpenseSelectParam toParam() {
        return ExpenseSelectParam.builder()
            .lawsuitId(lawsuitId)
            .page(page)
            .count(count)
            .contents(contents)
            .startSpeningAt(startSpeningAt)
            .endSpeningAt(endSpeningAt)
            .startAmount(startAmount)
            .endAmount(endAmount)
            .build();
    }
}
