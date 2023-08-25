package com.avg.lawsuitmanagement.lawsuit.dto;

import java.time.LocalDate;
import java.util.List;
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
public class LawsuitPrintLawsuitDto {

    private Long id;
    private String name;
    private String num;
    private String type;
    private String court;
    private Long commissionFee;
    private Long contingentFee;
    private String judgementResult;
    private LocalDate judgementDate;
    private List<String> clients;
    private List<String> members;

}
