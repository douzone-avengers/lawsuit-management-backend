package com.avg.lawsuitmanagement.member.repository;

import com.avg.lawsuitmanagement.member.dto.MemberDto;
import com.avg.lawsuitmanagement.member.dto.MemberDtoNonPass;
import com.avg.lawsuitmanagement.member.repository.param.InsertMemberParam;
import com.avg.lawsuitmanagement.member.repository.param.SearchEmployeeListParam;
import com.avg.lawsuitmanagement.member.repository.param.UpdateMemberParam;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapperRepository {

    MemberDto selectMemberByEmail(String email);
    void insertMember(InsertMemberParam param);
    List<MemberDto> selectMemberListById(List<Long> memberExistList);
    void updateMember(UpdateMemberParam param);
    List<MemberDtoNonPass> selectEmployeeListBySearchCondition(SearchEmployeeListParam param);
    int selectEmployeeListBySearchConditionCount(SearchEmployeeListParam param);
    MemberDtoNonPass selectMemberById(long employeeId);
    List<Long> selectMemberIdListByLawsuitId(long lawsuitId);

}
