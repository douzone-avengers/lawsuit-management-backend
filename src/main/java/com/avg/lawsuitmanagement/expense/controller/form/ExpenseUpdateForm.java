package com.avg.lawsuitmanagement.expense.controller.form;

import com.avg.lawsuitmanagement.expense.repository.param.ExpenseUpdateParam;
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
public class ExpenseUpdateForm {
    private LocalDate speningAt;
    private String contents;
    private Long amount;
}
