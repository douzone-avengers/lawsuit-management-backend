package com.avg.lawsuitmanagement.member.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class GetMemberListDto {
    int count;
    List<MemberDtoNonPass> memberDtoNonPassList;
}
