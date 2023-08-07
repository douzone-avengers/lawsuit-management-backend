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
public class UpdateReceptionParam {

    private Long id;
    private Boolean status;
    private String category;
    private String contents;
    private LocalDate receivedAt;
    private LocalDate deadline;

}
