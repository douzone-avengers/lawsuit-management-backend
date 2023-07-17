package com.avg.lawsuitmanagement.member.service;

import com.avg.lawsuitmanagement.common.util.SecurityUtil;
import com.avg.lawsuitmanagement.member.dto.MemberDto;
import com.avg.lawsuitmanagement.member.repository.MemberMapperRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberMapperRepository memberMapperRepository;

    public MemberDto getLoginMemberInfo() {
        String email = SecurityUtil.getCurrentLoginEmail();
        return memberMapperRepository.selectMemberByEmail(email);
    }
}
