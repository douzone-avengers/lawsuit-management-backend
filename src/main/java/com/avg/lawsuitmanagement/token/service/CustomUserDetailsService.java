package com.avg.lawsuitmanagement.token.service;

import com.avg.lawsuitmanagement.member.dto.MemberDto;
import com.avg.lawsuitmanagement.member.repository.MemberMapperRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberMapperRepository memberMapperRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) {

        MemberDto member = memberMapperRepository.selectMemberByEmail(username);
        if(member == null) {
            throw new UsernameNotFoundException("");
        }
        return new CustomUserDetail(member);
    }
}
