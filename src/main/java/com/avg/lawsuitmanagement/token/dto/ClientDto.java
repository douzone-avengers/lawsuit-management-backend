package com.avg.lawsuitmanagement.token.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class ClientDto {
    private long id;
    private String email;
    private String password;
    private boolean signupStatus;
    private String name;
    private String phone;
    private String address;
    private String  createdAt;
    private String updatedAt;
    private boolean isDeleted;
}
