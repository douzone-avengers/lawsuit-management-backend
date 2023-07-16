package com.avg.lawsuitmanagement.token.repository;

import com.avg.lawsuitmanagement.token.dto.MemberDto;
import com.avg.lawsuitmanagement.token.repository.param.SignUpParam;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TokenMapperRepository {
    MemberDto selectMemberByEmail(String email);
    void insertMember(SignUpParam param);
}
