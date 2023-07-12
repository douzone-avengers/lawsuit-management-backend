package com.avg.lawsuitmanagement.token.repository.param;

import com.avg.lawsuitmanagement.token.controller.form.ClientLoginForm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientLoginParam {

    private String email;
    private String password;

    public static ClientLoginParam of(ClientLoginForm form) {
        return ClientLoginParam.builder()
            .email(form.getEmail())
            .password(form.getPassword())
            .build();
    }
}
