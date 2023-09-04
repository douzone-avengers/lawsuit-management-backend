package com.avg.lawsuitmanagement.chat.dto;

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
public class UserSearchDetailResult {

    private Long id;
    private String email;
    private String name;
    private String hierarchy;
    private List<LawsuitBasicInfo> lawsuits;

}
