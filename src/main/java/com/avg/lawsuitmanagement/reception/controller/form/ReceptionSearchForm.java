package com.avg.lawsuitmanagement.reception.controller.form;

import com.avg.lawsuitmanagement.reception.repository.param.ReceptionSelectParam;
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
public class ReceptionSearchForm {

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

    public ReceptionSelectParam toParam() {
        return ReceptionSelectParam.builder()
            .lawsuitId(lawsuitId)
            .page(page)
            .count(count)
            .status(status)
            .category(category)
            .contents(contents)
            .startReceivedAt(startReceivedAt)
            .endReceivedAt(endReceivedAt)
            .startDeadline(startDeadline)
            .endDeadline(endDeadline)
            .build();
    }
    
}
