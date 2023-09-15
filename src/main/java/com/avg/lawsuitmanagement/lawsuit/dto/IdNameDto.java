package com.avg.lawsuitmanagement.lawsuit.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
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
public class IdNameDto {


    @EqualsAndHashCode.Include
    private Long id;
    private String name;

}
