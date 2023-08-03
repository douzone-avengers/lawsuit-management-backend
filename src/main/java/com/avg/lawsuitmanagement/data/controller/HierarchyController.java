package com.avg.lawsuitmanagement.data.controller;

import com.avg.lawsuitmanagement.data.dto.HierarchyDto;
import com.avg.lawsuitmanagement.data.service.HierarchyService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hierarchy")
public class HierarchyController {

    private final HierarchyService hierarchyService;

    @GetMapping()
    public ResponseEntity<List<HierarchyDto>> getHierarchyList() {
        return ResponseEntity.ok(hierarchyService.getHierarchyList());
    }
}
