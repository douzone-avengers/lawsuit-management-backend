package com.avg.lawsuitmanagement.lawsuit.controller.form;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Valid
public class SendLawsuitBookForm {
    @NotNull
    private List<String> toList;

    @NotNull
    private String pdfData;

}
