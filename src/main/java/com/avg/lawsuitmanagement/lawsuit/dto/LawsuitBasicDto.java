package com.avg.lawsuitmanagement.lawsuit.dto;

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
public class LawsuitBasicDto {

    private BasicLawsuitDto lawsuit;
    private List<BasicUserDto> employees;
    private List<BasicUserDto> clients;

}
