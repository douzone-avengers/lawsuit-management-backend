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
    private String lawsuitType;
    private Long lawsuitCommissionFee;
    private Long lawsuitContingentFee;
    private String lawsuitJudgementResult;
    private LocalDate lawsuitJudgementDate;
    private String courtName;
    private Long adviceId;
    private String adviceTitle;
    private String adviceContents;
    private LocalDate adviceDate;
    private Long adviceMemberId;
    private String adviceMemberName;
    private Long adviceClientId;
    private String adviceClientName;
    private Long memberId;
    private String memberEmail;
    private String memberName;
    private String memberPhone;
    private String memberAddress;
    private Long clientId;
    private String clientEmail;
    private String clientName;
    private String clientPhone;
    private String clientAddress;
    private Long expenseId;
    private String expenseContents;
    private Long expenseAmount;
    private LocalDate expenseDate;

}
