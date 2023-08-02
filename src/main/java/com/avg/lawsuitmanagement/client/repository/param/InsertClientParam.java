package com.avg.lawsuitmanagement.client.repository.param;

import com.avg.lawsuitmanagement.client.controller.form.InsertClientForm;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class InsertClientParam {

    private String email;
    private String name;
    private String phone;
    private String address;

    public static InsertClientParam of(InsertClientForm form) {
        return InsertClientParam.builder()
            .email(form.getEmail())
            .name(form.getName())
            .phone(form.getPhone())
            .address(form.getAddress())
            .build();
    }
}
