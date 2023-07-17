package com.avg.lawsuitmanagement.member.repository.param;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SignUpParam {
    private String email;
    private String password;
    private String name;
    private String phone;
    private long hierarchyId;
    private String address;
    private long roleId;
}