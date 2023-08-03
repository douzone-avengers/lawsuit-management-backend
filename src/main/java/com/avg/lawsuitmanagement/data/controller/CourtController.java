package com.avg.lawsuitmanagement.data.controller;

import com.avg.lawsuitmanagement.data.dto.CourtDto;
import com.avg.lawsuitmanagement.data.service.CourtService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/court")
public class CourtController {

    private final CourtService courtService;

    @GetMapping
    public ResponseEntity<List<CourtDto>> getCourtList() {
        return ResponseEntity.ok(courtService.getCourtList());
    }

    @GetMapping("/{courtId}")
    public ResponseEntity<CourtDto> getCourt(@PathVariable long courtId) {
        return ResponseEntity.ok(courtService.getCourt(courtId));
    }
}
