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
public class RoomBasicRaw {

    private Long roomId;
    private String roomType;
    private String roomName;
    private Boolean roomIsShow;
    private Long userId;
    private String userEmail;
    private String userName;
    private String userHierarchy;

}
