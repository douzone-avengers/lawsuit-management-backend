package com.avg.lawsuitmanagement.advice.controller.form;

import com.avg.lawsuitmanagement.lawsuit.dto.IdNameDto;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class UpdateAdviceInfoForm {



    @NotNull
    private List<IdNameDto> memberIdList;
    @NotNull
    private List<IdNameDto> clientIdList;
    @NotNull
    private long lawsuitId;
    @NotBlank
    private String title;
    @NotBlank
    private String contents;
    @NotBlank
    private String advicedAt;


}
