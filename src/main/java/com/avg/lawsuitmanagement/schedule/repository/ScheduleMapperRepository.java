package com.avg.lawsuitmanagement.schedule.repository;

import com.avg.lawsuitmanagement.schedule.dto.ScheduleInfoRawDto;
import com.avg.lawsuitmanagement.schedule.dto.ScheduleRawDto;
import com.avg.lawsuitmanagement.schedule.repository.param.ScheduleSelectParam;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ScheduleMapperRepository {

    List<ScheduleRawDto> select(ScheduleSelectParam param);

    List<ScheduleInfoRawDto> selectInfo(Long receptionId);

}
