package com.avg.lawsuitmanagement.lawsuit.repository.param;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class InsertLawsuitClientMemberIdParam {
    private Long lawsuitId;
    private List<Long> clientId;
    private List<Long> memberId;

    public static InsertLawsuitClientMemberIdParam of(long lawsuitId, List<Long> clientIdList, List<Long> memberIdList) {
        return InsertLawsuitClientMemberIdParam.builder()
            .lawsuitId(lawsuitId)
            .clientId(clientIdList)
            .memberId(memberIdList)
            .build();
    }

}
