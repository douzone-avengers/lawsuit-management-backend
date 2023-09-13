package com.avg.lawsuitmanagement.statistics.repository;

import com.avg.lawsuitmanagement.statistics.dto.LawsuitStatusDto;
import com.avg.lawsuitmanagement.statistics.dto.RevenueDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StatisticsMapperRepository {

    LawsuitStatusDto countLawsuitsStatusByMemberId(long memberId);
    RevenueDto selectRevenueByMemberId(long memberId);

}
