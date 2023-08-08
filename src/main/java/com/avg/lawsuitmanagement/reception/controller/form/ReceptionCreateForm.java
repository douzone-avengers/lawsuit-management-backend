package com.avg.lawsuitmanagement.reception.controller.form;

import com.avg.lawsuitmanagement.reception.repository.param.ReceptionInsertParam;
import java.time.LocalDate;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
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
public class ReceptionCreateForm {

    @Null
    private Long id;
    private Long lawsuitId;
    private Boolean status;
    @Pattern(regexp = "fixed|scheduled")
    private String category;
    private String contents;
    private LocalDate receivedAt;
    private LocalDate deadline;

    public ReceptionInsertParam toParam() {
        return ReceptionInsertParam.builder()
            .lawsuitId(lawsuitId)
            .status(status)
            .category(category)
            .contents(contents)
            .receivedAt(receivedAt)
            .deadline(deadline)
            .build();
    }
}
