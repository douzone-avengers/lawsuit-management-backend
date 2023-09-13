package com.avg.lawsuitmanagement.client.repository.param;

import com.avg.lawsuitmanagement.client.controller.form.InsertClientForm;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ReRegisterClientParam {
    private Long clientId;
    private String email;
    private String name;
    private String phone;
    private String address;
    private String addressDetail;
    private boolean isDeleted;

    public static ReRegisterClientParam of(Long clientId, InsertClientForm form) {
        return ReRegisterClientParam.builder()
            .clientId(clientId)
            .email(form.getEmail())
            .name(form.getName())
            .phone(form.getPhone())
            .address(form.getAddress())
            .addressDetail(form.getAddressDetail())
            .isDeleted(false)
            .build();
    }
}
