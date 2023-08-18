package com.avg.lawsuitmanagement.expense.repository.param;

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
public class ExpenseInsertParam {

    private Long id;
    private Long lawsuitId;
    private String contents;
    private Long amount;
    private LocalDate speningAt;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}
