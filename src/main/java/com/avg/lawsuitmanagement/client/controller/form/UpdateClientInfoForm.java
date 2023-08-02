package com.avg.lawsuitmanagement.client.controller.form;

import com.avg.lawsuitmanagement.client.repository.param.UpdateClientInfoParam;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateClientInfoForm {
    @Email
    private String email;
    @Size(min=2, max=10)
    private String name;
    @Size(min=13, max=13)
    private String phone;
    @NotBlank
    private String address;
}
