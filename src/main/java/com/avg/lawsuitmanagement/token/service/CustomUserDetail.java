package com.avg.lawsuitmanagement.token.service;

import com.avg.lawsuitmanagement.token.dto.ClientDto;
import com.avg.lawsuitmanagement.token.type.UserType;
import java.util.Arrays;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetail implements UserDetails {

    private final UserType userType;
    private final String userName; //ex)ksj2083@naver.com#CLIENT
    private final String password;


    public CustomUserDetail(ClientDto clientDto) {
        this.userType = UserType.CLIENT;
        this.userName = clientDto.getEmail();
        this.password = clientDto.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //현재 하드코딩, 수정 예정!
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_CLIENT"));
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
