package com.avg.lawsuitmanagement.expense.dto;

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
public class ExpenseDto {
    private Long id;
    private Long lawsuitId; // fk
    private String contents;
    private Long amount;
    private LocalDate speningAt;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private Boolean isDeleted;

}
