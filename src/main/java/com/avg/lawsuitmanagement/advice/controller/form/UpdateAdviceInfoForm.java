package com.avg.lawsuitmanagement.advice.controller.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
@Getter
@Setter
public class UpdateAdviceInfoForm {

    @NotBlank
    private long memberId;
    @NotBlank
    private long clientId;
    @NotBlank
    private String title;
    @NotBlank
    private String contnets;
    @NotBlank
    private String advicedAt;

}
