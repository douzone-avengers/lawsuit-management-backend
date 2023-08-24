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
public class LawsuitPrintAdvicePreDto {

    private Long id;
    private String title;
    private String contents;
    private LocalDate date;
    private List<IdNameDto> memberIdNames;
    private List<IdNameDto> clientIdNames;

}
