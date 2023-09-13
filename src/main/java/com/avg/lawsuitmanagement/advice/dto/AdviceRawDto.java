package com.avg.lawsuitmanagement.advice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AdviceRawDto {

    private Long id;
    private Long lawsuitId;
    private String title;
    private String contents;
    private String advicedAt;
    private Long clientId;
    private String clientName;
    private Long memberId;
    private String memberName;
}
