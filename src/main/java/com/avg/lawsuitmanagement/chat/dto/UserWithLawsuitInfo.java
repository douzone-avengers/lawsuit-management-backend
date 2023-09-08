package com.avg.lawsuitmanagement.chat.dto;

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
public class UserWithLawsuitInfo {

    private Long id;
    private String email;
    private String name;
    private String hierarchy;
    private Long lawsuitId;
    private String lawsuitType;
    private String lawsuitNum;
    private String lawsuitName;
    private String lawsuitStatus;

}
