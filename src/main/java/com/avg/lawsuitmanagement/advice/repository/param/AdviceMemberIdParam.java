package com.avg.lawsuitmanagement.advice.repository.param;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class AdviceMemberIdParam {
    private Long adviceId;
    private List<Long> memberId;

    public static AdviceMemberIdParam of(long adviceId, List<Long> memberIdList){
        return AdviceMemberIdParam.builder()
                .adviceId(adviceId)
                .memberId(memberIdList)
                .build();
    }
}
