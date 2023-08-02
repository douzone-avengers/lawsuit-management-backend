package com.avg.lawsuitmanagement.client.repository.param;

import com.avg.lawsuitmanagement.client.controller.form.UpdateClientInfoForm;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UpdateClientInfoParam {
    private String email;
    private String name;
    private String phone;
    private String address;

    public static UpdateClientInfoParam of(UpdateClientInfoForm form) {
        return UpdateClientInfoParam.builder()
            .email(form.getEmail())
            .name(form.getName())
            .phone(form.getPhone())
            .address(form.getAddress())
            .build();
    }
}
