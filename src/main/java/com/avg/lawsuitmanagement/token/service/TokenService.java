package com.avg.lawsuitmanagement.token.service;

import com.avg.lawsuitmanagement.token.controller.form.LoginForm;
import com.avg.lawsuitmanagement.token.dto.JwtTokenDto;
import com.avg.lawsuitmanagement.token.provider.TokenProvider;
import com.avg.lawsuitmanagement.token.repository.TokenMapperRepository;
import com.avg.lawsuitmanagement.token.repository.param.RefreshTokenParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenService {

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenMapperRepository tokenMapperRepository;

    public JwtTokenDto login(LoginForm form) {
        //실질적인 검증
        Authentication authentication = authenticate(
            new UsernamePasswordAuthenticationToken(form.getEmail(), form.getPassword()));

        //무사히 통과했다면 jwt 토큰 발행
        JwtTokenDto jwtTokenDto = tokenProvider.createTokenDto(authentication);

        //refreshToken DB에 저장
        tokenMapperRepository.insertRefreshToken(RefreshTokenParam.builder()
            .key(form.getEmail())
            .value(jwtTokenDto.getRefreshToken())
            .build());

        return jwtTokenDto;
    }

    private Authentication authenticate(UsernamePasswordAuthenticationToken authenticationToken) {
        //실질적인 검증단계
        //authenticate 메소드가 실행 될 때 CustomUserDetailService.loadUserByUsername이 실행된다.
        //즉, 사용자가 입력한 정보와 db 정보가 일치하는지 검증한다.
        return authenticationManagerBuilder.getObject()
            .authenticate(authenticationToken);
    }
}
