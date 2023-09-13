package com.avg.lawsuitmanagement.client.repository.param;

import com.avg.lawsuitmanagement.client.controller.form.UpdateClientInfoForm;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UpdateClientInfoParam {
    private Long clientId;
    private String email;
    private String name;
    private String phone;
    private String address;
    private String addressDetail;

    public static UpdateClientInfoParam of(Long clientId, UpdateClientInfoForm form) {
        return UpdateClientInfoParam.builder()
            .clientId(clientId)
            .email(form.getEmail())
            .name(form.getName())
            .phone(form.getPhone())
            .address(form.getAddress())
            .addressDetail(form.getAddressDetail())
            .build();
    }
}
