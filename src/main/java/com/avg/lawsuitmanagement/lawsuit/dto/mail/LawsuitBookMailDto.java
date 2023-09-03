package com.avg.lawsuitmanagement.lawsuit.dto.mail;

import com.avg.lawsuitmanagement.lawsuit.controller.form.SendLawsuitBookForm;
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
public class LawsuitBookMailDto {

    private List<String> toList;
    private String fullFilePath;
    private LawsuitDto lawsuitDto;

    public static LawsuitBookMailDto of(LawsuitDto lawsuitDto, String fullFilePath, SendLawsuitBookForm form) {
        return LawsuitBookMailDto.builder()
            .toList(form.getToList())
            .lawsuitDto(lawsuitDto)
            .fullFilePath(fullFilePath)
            .build();
    }
}
