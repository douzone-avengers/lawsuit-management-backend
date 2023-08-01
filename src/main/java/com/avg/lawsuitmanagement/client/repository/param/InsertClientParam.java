package com.avg.lawsuitmanagement.client.repository.param;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class InsertClientParam {

    private String email;
    private String name;
    private String phone;
    private String address;
}
