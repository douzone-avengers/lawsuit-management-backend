package com.avg.lawsuitmanagement.data.repository;

import com.avg.lawsuitmanagement.data.dto.CourtDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CourtMapperRepository {

    List<CourtDto> selectCourtList();
    CourtDto selectCourt(long id);
}
