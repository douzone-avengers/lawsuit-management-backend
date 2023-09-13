package com.avg.lawsuitmanagement.data.repository;

import com.avg.lawsuitmanagement.data.dto.RoleDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoleMapperRepository {

    List<RoleDto> selectRoleList();
    RoleDto selectRoleById(long id);
}
