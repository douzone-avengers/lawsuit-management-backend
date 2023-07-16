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
    private String name;
    private String phone;
    private String address;
    private Long memberId; // 회원가입 된 의뢰인일 경우 관계 존재
    private String createdAt;
    private String updatedAt;
    private boolean isDeleted;
}
