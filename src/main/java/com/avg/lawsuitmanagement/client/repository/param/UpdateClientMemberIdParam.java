package com.avg.lawsuitmanagement.client.repository.param;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UpdateClientMemberIdParam {

    private long clientId;
    private long memberId;
}
