package com.avg.lawsuitmanagement.member.repository;

import com.avg.lawsuitmanagement.member.dto.MemberDto;
import com.avg.lawsuitmanagement.member.repository.param.InsertMemberParam;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapperRepository {

    MemberDto selectMemberByEmail(String email);
    void insertMember(InsertMemberParam param);
    List<MemberDto> selectMemberListById(List<Long> memberExistList);
}
