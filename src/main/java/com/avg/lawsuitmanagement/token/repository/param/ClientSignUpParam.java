package com.avg.lawsuitmanagement.token.repository.param;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ClientSignUpParam {
    private String email;
    private String password;
    private String name;
    private String phone;
    private String address;
}
