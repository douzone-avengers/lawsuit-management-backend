package com.avg.lawsuitmanagement.advice.repository.param;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class InsertAdviceClientIdParam {
    private Long adviceId;
    private List<Long> clientId;

    public static InsertAdviceClientIdParam of(long adviceId, List<Long> clientIdList){
        return InsertAdviceClientIdParam.builder()
                .adviceId(adviceId)
                .clientId(clientIdList)
                .build();
    }
}
