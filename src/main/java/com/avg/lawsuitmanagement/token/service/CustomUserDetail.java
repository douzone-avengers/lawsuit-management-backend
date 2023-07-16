package com.avg.lawsuitmanagement.token.service;


import com.avg.lawsuitmanagement.token.dto.MemberDto;
import com.avg.lawsuitmanagement.token.type.AuthRole;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetail implements UserDetails {

    private final MemberDto member;

    public CustomUserDetail(MemberDto member) {
        this.member = member;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        if(member.getRole().equals("CLIENT")) {
            authorities.add(new SimpleGrantedAuthority(AuthRole.ROLE_CLIENT.name()));
            return authorities;
        }

        authorities.add(new SimpleGrantedAuthority(AuthRole.ROLE_EMPLOYEE.name()));
        if(member.getRole().equals("ADMIN")) {
            authorities.add(new SimpleGrantedAuthority(AuthRole.ROLE_ADMIN.name()));
        }

        return authorities;
    }

    @Override
    public String getPassword() {
        return this.member.getPassword();
    }

    @Override
    public String getUsername() {
        return this.member.getEmail();
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
