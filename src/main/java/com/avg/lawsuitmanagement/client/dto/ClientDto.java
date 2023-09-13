package com.avg.lawsuitmanagement.client.dto;

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
    private String addressDetail;
    private String createdAt;
    private String updatedAt;
    private long memberId; // fk
}
