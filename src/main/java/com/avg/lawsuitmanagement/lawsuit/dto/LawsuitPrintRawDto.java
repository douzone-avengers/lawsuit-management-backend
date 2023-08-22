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
public class LawsuitPrintRawDto {

    private Long lawsuitId;
    private String lawsuitName;
    private String lawsuitNum;
    private Long lawsuitCommissionFee;
    private Long lawsuitContingentFee;
    private String lawsuitResult;
    private String lawsuitJudgementResult;
    private LocalDate lawsuitJudgementDate;
    private String courtName;
    private Long adviceId;
    private String adviceTitle;
    private String adviceContents;
    private LocalDate adviceDate;
    private Long memberId;
    private String memberName;
    private Long clientId;
    private String clientName;
    private Long expenseId;
    private String expenseContents;
    private Long expenseAmount;
    private LocalDate expenseDate;

}
