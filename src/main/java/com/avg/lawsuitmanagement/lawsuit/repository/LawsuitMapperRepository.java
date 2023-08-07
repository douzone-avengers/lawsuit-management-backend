package com.avg.lawsuitmanagement.lawsuit.repository;

import com.avg.lawsuitmanagement.lawsuit.dto.LawsuitDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LawsuitMapperRepository {

    LawsuitDto selectLawsuitById(long lawsuitId);

}
