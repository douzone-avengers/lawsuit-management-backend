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
    private String hierarchy; //직급, 조인필요
    private String phone; //전화번호
    private String address; //주소
    private String role; //역할, 조인필요
    private String createdAt; //생성일
    private String updatedAt; //수정일
    private boolean isDeleted; //삭제여부
}
