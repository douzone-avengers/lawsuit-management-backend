package com.avg.lawsuitmanagement.chat.data;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
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
public class MessageRequest {

    private String senderName;
    private String message;

    public MessageResponse toResponse() {
        return MessageResponse.builder()
            .id(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
            .senderName(senderName)
            .message(message)
            .build();
    }
}
