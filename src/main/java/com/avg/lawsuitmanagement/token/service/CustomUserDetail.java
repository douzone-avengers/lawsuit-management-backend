package com.avg.lawsuitmanagement.token.service;

import com.avg.lawsuitmanagement.token.dto.ClientDto;
import com.avg.lawsuitmanagement.token.dto.EmployeeDto;
import com.avg.lawsuitmanagement.token.type.AuthRole;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetail implements UserDetails {

    private final String userName; //ex)ksj2083@naver.com#CLIENT
    private final String password;
    private final List<SimpleGrantedAuthority> authRoleList;


    public CustomUserDetail(ClientDto clientDto) {
        this.userName = clientDto.getEmail();
        this.password = clientDto.getPassword();
        this.authRoleList = List.of(new SimpleGrantedAuthority(AuthRole.ROLE_CLIENT.name()));
    }

    public CustomUserDetail(EmployeeDto employeeDto) {
        this.userName = employeeDto.getEmail();
        this.password = employeeDto.getPassword();

        this.authRoleList = new ArrayList<>();
        authRoleList.add(new SimpleGrantedAuthority(AuthRole.ROLE_USER.name()));

        if(employeeDto.getRole().equals("ADMIN")) {
            authRoleList.add(new SimpleGrantedAuthority(AuthRole.ROLE_ADMIN.name()));
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authRoleList;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    /***
     * 계정 만료 여부
     * @return true:만료 안됨, false: 만료됨
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
