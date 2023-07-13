package com.avg.lawsuitmanagement.token.repository.param;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EmployeeSignUpParam {
    private String email;
    private String password;
    private String name;
    private String phone;
    private long hierarchyId;
    private String address;
    private long roleId;
}