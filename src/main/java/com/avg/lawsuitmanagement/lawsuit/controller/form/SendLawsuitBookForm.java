package com.avg.lawsuitmanagement.lawsuit.controller.form;

import java.util.Date;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Valid
public class SendLawsuitBookForm {
    @Email
    @NotNull
    private List<String> toList;
}
