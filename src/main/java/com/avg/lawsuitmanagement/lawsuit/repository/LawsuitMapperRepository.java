package com.avg.lawsuitmanagement.lawsuit.repository;

import com.avg.lawsuitmanagement.lawsuit.dto.LawsuitDto;
import com.avg.lawsuitmanagement.lawsuit.repository.param.InsertLawsuitParam;
import com.avg.lawsuitmanagement.member.dto.MemberDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LawsuitMapperRepository {
    void insertLawsuit(InsertLawsuitParam param);
    List<LawsuitDto> selectLawsuitList();
}
