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
public class ExpenseSelectParam {
    private Long lawsuitId;
    private Long page;
    private Long count;
    private String contents;
    private LocalDate startSpeningAt;
    private LocalDate endSpeningAt;
    private Long startAmount;
    private Long endAmount;
    private String sortKey;
    private String sortOrder;
}
