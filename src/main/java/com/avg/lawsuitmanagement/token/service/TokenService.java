package com.avg.lawsuitmanagement.token.service;

import com.avg.lawsuitmanagement.token.controller.form.ClientLoginForm;
import com.avg.lawsuitmanagement.token.dto.JwtTokenDto;
import com.avg.lawsuitmanagement.token.provider.TokenProvider;
import com.avg.lawsuitmanagement.token.type.UserType;
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

    public JwtTokenDto clientLogin(ClientLoginForm form) throws RuntimeException {

        //ex)ksj2083@naver.com#CLIENT
        String userName = form.getEmail() + "#" + UserType.CLIENT.name();

        UsernamePasswordAuthenticationToken authenticationToken
            = new UsernamePasswordAuthenticationToken(userName, form.getPassword());

        //실질적인 검증단계
        //authenticate 메소드가 실행 될 때 CustomUserDetailService.loadUserByUsername이 실행된다.
        //즉, 사용자가 입력한 정보와 db 정보가 일치하는지 검증한다.
        Authentication authentication = null;
        try {
            authentication = authenticationManagerBuilder.getObject()
                .authenticate(authenticationToken);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new RuntimeException("로그인 실패");
        }

        //무사히 통과했다면 jwt 토큰 발행
        JwtTokenDto jwtTokenDto = tokenProvider.createTokenDto(authentication);

        //refreshToken DB에 저장 구현 예정
        return jwtTokenDto;
    }
}
