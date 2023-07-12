package com.avg.lawsuitmanagement.token.service;

import com.avg.lawsuitmanagement.token.repository.TokenMapperRepository;
import com.avg.lawsuitmanagement.token.type.UserType;
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

    private final TokenMapperRepository tokenMapperRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        String[] split = username.split("#");
        String email = split[0];
        UserType userType = UserType.valueOf(split[1]);

        //고객 로그인일 경우
        if (userType == UserType.CLIENT) {
            return new CustomUserDetail(
                tokenMapperRepository.selectClientById(email));

        }

        //직원 로그인일 경우
        log.error("직원 로그인으로 판정됨");
        return null;
    }
}
