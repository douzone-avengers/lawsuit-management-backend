package com.avg.lawsuitmanagement.lawsuit.dto.mail;

import com.avg.lawsuitmanagement.lawsuit.controller.form.SendBillForm;
import com.avg.lawsuitmanagement.lawsuit.dto.LawsuitDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class BillMailDto {

    private List<String> toList;
    private String fullFilePath;
    private LawsuitDto lawsuitDto;

    public static BillMailDto of(LawsuitDto lawsuitDto, String fullFilePath, SendBillForm form) {
        return BillMailDto.builder()
            .toList(form.getToList())
            .lawsuitDto(lawsuitDto)
            .fullFilePath(fullFilePath)
            .build();
    }
}
