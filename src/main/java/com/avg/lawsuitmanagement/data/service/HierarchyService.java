package com.avg.lawsuitmanagement.data.service;

import com.avg.lawsuitmanagement.data.dto.HierarchyDto;
import com.avg.lawsuitmanagement.data.repository.HierarchyMapperRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class HierarchyService {
    private final HierarchyMapperRepository hierarchyMapperRepository;

    public List<HierarchyDto> getHierarchyList() {
        return hierarchyMapperRepository.selectHierarchyList();
    }
}
