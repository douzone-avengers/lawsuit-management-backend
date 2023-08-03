package com.avg.lawsuitmanagement.data.service;

import com.avg.lawsuitmanagement.common.custom.CustomRuntimeException;
import com.avg.lawsuitmanagement.common.exception.type.ErrorCode;
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

    public HierarchyDto getHierarchy(long id) {
        HierarchyDto hierarchyDto = hierarchyMapperRepository.selectHierarchyById(id);
        if(hierarchyDto == null) {
            throw new CustomRuntimeException(ErrorCode.HIERARCHY_NOT_FOUND);
        }
        return hierarchyDto;
    }
}
