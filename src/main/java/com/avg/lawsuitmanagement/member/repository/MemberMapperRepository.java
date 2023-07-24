package com.avg.lawsuitmanagement.member.repository;

import com.avg.lawsuitmanagement.member.dto.MemberDto;
import com.avg.lawsuitmanagement.member.repository.param.SignUpParam;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapperRepository {

    MemberDto selectMemberByEmail(String email);
    void insertMember(SignUpParam param);
}