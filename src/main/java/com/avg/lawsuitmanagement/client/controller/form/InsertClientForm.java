package com.avg.lawsuitmanagement.client.controller.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InsertClientForm {

    @Email
    private String email;
    @Size(min=2, max=10)
    private String name;
    @Size(min=13, max=13)
    private String phone;
    @NotBlank
    private String address;
}
