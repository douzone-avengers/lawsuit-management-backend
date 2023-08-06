package com.avg.lawsuitmanagement.member.controller.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EmployeeSignUpForm {
    @NotBlank
    private String promotionKey;
    @Email
    private String email;
    @NotBlank
    private String password;
    @Size(min=2, max=10)
    private String name;
    @Size(min=13, max=13)
    private String phone;
    @NotBlank
    private String address;
    @NotBlank
    private long hierarchyId;
    @NotBlank
    private long roleId;
}
