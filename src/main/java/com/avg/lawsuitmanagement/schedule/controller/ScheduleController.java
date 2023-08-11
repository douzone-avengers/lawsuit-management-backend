package com.avg.lawsuitmanagement.schedule.controller;

import com.avg.lawsuitmanagement.schedule.controller.form.ScheduleSearchForm;
import com.avg.lawsuitmanagement.schedule.dto.ScheduleDto;
import com.avg.lawsuitmanagement.schedule.dto.ScheduleInfoDto;
import com.avg.lawsuitmanagement.schedule.service.ScheduleService;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping
    public ResponseEntity<?> search(
        @RequestParam(name = "start-deadline", required = false)
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate startDeadline,
        @RequestParam(name = "end-deadline", required = false)
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate endDeadline
    ) {
        ScheduleSearchForm form = ScheduleSearchForm.builder()
            .startDeadline(startDeadline)
            .endDeadline(endDeadline)
            .build();

        List<ScheduleDto> result = scheduleService.search(form);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/info/{id}")
    public ResponseEntity<?> searchInfo(@PathVariable(name = "id") Long receptionId) {
        ScheduleInfoDto result = scheduleService.searchInfo(receptionId);
        return ResponseEntity.ok(result);
    }

}
