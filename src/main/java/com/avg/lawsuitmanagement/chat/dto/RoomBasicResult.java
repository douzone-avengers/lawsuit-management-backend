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
public class RoomBasicResult {

    private Long id;
    private String type;
    private String name;
    private List<UserBasicInfo> users;

}
