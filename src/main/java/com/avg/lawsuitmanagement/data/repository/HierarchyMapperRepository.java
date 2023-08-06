package com.avg.lawsuitmanagement.data.repository;

import com.avg.lawsuitmanagement.data.dto.HierarchyDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HierarchyMapperRepository {

    List<HierarchyDto> selectHierarchyList();
    HierarchyDto selectHierarchyById(long id);
}
