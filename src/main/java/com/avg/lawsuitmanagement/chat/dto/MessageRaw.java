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
public class MessageRaw {

    private Long id;
    private Long roomId;
    private Long senderId;
    private String content;
    private String createdAt;

}
