package com.avg.lawsuitmanagement.lawsuit.controller.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetEmployeeLawsuitForm {
    private Integer curPage;
    private Integer rowsPerPage;
    private String searchWord;
}