package com.avg.lawsuitmanagement.lawsuit.controller.form;

import com.avg.lawsuitmanagement.lawsuit.type.LawsuitStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetClientLawsuitForm {
    private Integer curPage;
    private Integer rowsPerPage;
    private String searchWord;
    private LawsuitStatus lawsuitStatus;
}
