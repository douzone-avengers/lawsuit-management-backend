package com.avg.lawsuitmanagement.advice.repository.param;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class InsertAdviceClientMemberIdParam {


    private Long adviceId;
    private List<Long> clientId;
    private List<Long> memberId;

    public static InsertAdviceClientMemberIdParam of(long adviceId, List<Long> clientIdList, List<Long> memberIdList){
        return InsertAdviceClientMemberIdParam.builder()
                .adviceId(adviceId)
                .clientId(clientIdList)
                .memberId(memberIdList)
                .build();
    }
}