package com.avg.lawsuitmanagement.advice.controller.form;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
public class AdviceListForm {

    private String sortKey;
    private String sortOrder;


    @NotNull
    private Integer curPage;
    @NotNull
    private Integer rowsPerPage;


}
