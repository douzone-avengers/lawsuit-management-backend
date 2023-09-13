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
public class LawsuitPrintAdviceDto {

    private Long id;
    private String title;
    private String contents;
    private LocalDate date;
    private List<String> memberNames;
    private List<String> clientNames;
    
}
