package com.avg.lawsuitmanagement.statistics.service;

import com.avg.lawsuitmanagement.statistics.dto.LawsuitStatusDto;
import com.avg.lawsuitmanagement.statistics.dto.RevenueDto;
import com.avg.lawsuitmanagement.statistics.repository.StatisticsMapperRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final StatisticsMapperRepository statisticsMapperRepository;

    public LawsuitStatusDto getLawsuitsStatus(long memberId) {
        return statisticsMapperRepository.countLawsuitsStatusByMemberId(memberId);
    }

    public RevenueDto getRevenue(long memberId) {
        return statisticsMapperRepository.selectRevenueByMemberId(memberId);
    }
}
