package com.avg.lawsuitmanagement.reception.controller.form;

import com.avg.lawsuitmanagement.reception.repository.param.ReceptionUpdateParam;
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
public class ReceptionEditForm {

    private Boolean status;
    private String category;
    private String contents;
    private LocalDate receivedAt;
    private LocalDate deadline;

    public ReceptionUpdateParam toParam(Long id) {
        return ReceptionUpdateParam.builder()
            .id(id)
            .status(status)
            .category(category)
            .contents(contents)
            .receivedAt(receivedAt)
            .deadline(deadline)
            .build();
    }

}
