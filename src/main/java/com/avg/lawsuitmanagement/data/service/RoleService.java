package com.avg.lawsuitmanagement.data.service;

import com.avg.lawsuitmanagement.common.custom.CustomRuntimeException;
import com.avg.lawsuitmanagement.common.exception.type.ErrorCode;
import com.avg.lawsuitmanagement.data.dto.RoleDto;
import com.avg.lawsuitmanagement.data.repository.RoleMapperRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleService {
    private final RoleMapperRepository roleMapperRepository;

    public List<RoleDto> getRoleList() {
        return roleMapperRepository.selectRoleList();
    }

    public RoleDto getRole(long id) {
        RoleDto roleDto = roleMapperRepository.selectRoleById(id);
        if(roleDto == null) {
            throw new CustomRuntimeException(ErrorCode.ROLE_NOT_FOUND);
        }
        return roleDto;
    }
}
