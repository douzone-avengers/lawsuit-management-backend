package com.avg.lawsuitmanagement.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class MemberDto {
    private long id;
    private String email; //이메일
    private String password; //비밀번호
    private String name; //이름
    private long hierarchyId; //직급 아이디
    private String phone; //전화번호
    private String address; //주소
    private String addressDetail; // 상세주소
    private long roleId; //역할 아이디
    private String createdAt; //생성일
    private String updatedAt; //수정일
    private boolean isDeleted; //삭제여부
}
