package com.avg.lawsuitmanagement.data.controller;

import com.avg.lawsuitmanagement.data.dto.RoleDto;
import com.avg.lawsuitmanagement.data.service.RoleService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/role")
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<List<RoleDto>> getRoleList() {
        return ResponseEntity.ok(roleService.getRoleList());
    }

    @GetMapping("/{roleId}")
    public ResponseEntity<RoleDto> getRole(@PathVariable long roleId) {
        return ResponseEntity.ok(roleService.getRole(roleId));
    }
}
