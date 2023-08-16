package com.avg.lawsuitmanagement.advice.repository.param;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class InsertAdviceMemberIdParam {
    private Long adviceId;
    private List<Long> memberId;

    public static InsertAdviceMemberIdParam of(long adviceId, List<Long> memberIdList){
        return InsertAdviceMemberIdParam.builder()
                .adviceId(adviceId)
                .memberId(memberIdList)
                .build();
    }
}
