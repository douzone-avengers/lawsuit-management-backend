package com.avg.lawsuitmanagement.advice.dto;


import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AdviceDetailResponseDto {
    private Long adviceId;
    private String title;
    private String contents;
    private String advicedAt;
    private List<ClientIdNameDto> clients;
    private List<MemberIdNameDto> members;
}
