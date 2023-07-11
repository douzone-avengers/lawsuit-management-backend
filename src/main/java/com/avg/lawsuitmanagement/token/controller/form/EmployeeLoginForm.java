package com.avg.lawsuitmanagement.token.controller.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeLoginForm {
    private String email;
    private String password;
}
