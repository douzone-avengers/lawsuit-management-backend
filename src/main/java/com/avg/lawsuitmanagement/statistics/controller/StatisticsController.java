package com.avg.lawsuitmanagement.statistics.controller;

import com.avg.lawsuitmanagement.statistics.dto.LawsuitStatusDto;
import com.avg.lawsuitmanagement.statistics.dto.RevenueDto;
import com.avg.lawsuitmanagement.statistics.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/statistics")
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping("/lawsuits-status/employees/{employeeId}")
    public ResponseEntity<LawsuitStatusDto> getLawsuitsStatus(@PathVariable Long employeeId) {
        return ResponseEntity.ok(statisticsService.getLawsuitsStatus(employeeId));
    }

    @GetMapping("/revenues/employees/{employeeId}")
    public ResponseEntity<RevenueDto> getRevenue(@PathVariable Long employeeId) {
        return ResponseEntity.ok(statisticsService.getRevenue(employeeId));
    }
}
