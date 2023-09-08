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
public class UserBasicInfo {

    private Long id;
    private String email;
    private String name;
    private String hierarchy;

}
