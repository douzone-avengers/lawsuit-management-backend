package com.avg.lawsuitmanagement.advice.repository.param;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class DeleteAdviceClientMemberIdParam {
    private Long adviceId;
    private List<Long> clientId;
    private List<Long> memberId;

    public static DeleteAdviceClientMemberIdParam of(long adviceId, List<Long> clientIdList, List<Long> memberIdList){
        return DeleteAdviceClientMemberIdParam.builder()
                .adviceId(adviceId)
                .clientId(clientIdList)
                .memberId(memberIdList)
                .build();
    }
}
