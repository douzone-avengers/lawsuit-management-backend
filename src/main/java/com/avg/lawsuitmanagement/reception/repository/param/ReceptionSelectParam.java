package com.avg.lawsuitmanagement.reception.repository.param;

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
public class ReceptionSelectParam {

    private Long lawsuitId;
    private Long page;
    private Long count;
    private Boolean status;
    private String category;
    private String contents;
    private LocalDate startReceivedAt;
    private LocalDate endReceivedAt;
    private LocalDate startDeadline;
    private LocalDate endDeadline;

}
