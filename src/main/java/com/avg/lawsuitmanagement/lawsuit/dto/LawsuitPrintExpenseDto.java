package com.avg.lawsuitmanagement.lawsuit.dto;

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
public class LawsuitPrintExpenseDto {

    private Long id;
    private String contents;
    private Long amount;
    private LocalDate date;

}
