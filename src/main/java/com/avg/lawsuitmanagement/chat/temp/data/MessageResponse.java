package com.avg.lawsuitmanagement.chat.temp.data;

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
public class MessageResponse {

    private Long id;
    private String senderName;
    private String message;

}
