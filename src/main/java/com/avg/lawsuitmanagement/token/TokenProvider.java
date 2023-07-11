package com.avg.lawsuitmanagement.token;


import com.avg.lawsuitmanagement.token.dto.JwtTokenDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class TokenProvider {
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 10; //10초
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60; //60분
    private final Key key; //토큰 암호화에 사용되는 비밀키

    public TokenProvider(@Value("${jwt.secret}") String secretKey) {
        //String으로 바로 사용 시, 규칙 위반 오류 발생
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    /***
     * 로그인 시, accessToken과 refreshToken을 발행하는 메소드
     * refreshToken은 DB에 저장된다.
     */
    public JwtTokenDto createTokenDto(Authentication authentication) {

        long now = (new Date()).getTime();

        /*
        String accessToken = createAccessToken(now, authentication);
        String refreshToken = createRefreshToken(now, authentication);

        TokenDto tokenDto= new TokenDto(accessToken, refreshToken);
        return tokenDto;
        */

        return JwtTokenDto.builder()
            .accessToken(createAccessToken(now, authentication))
            .refreshToken(createRefreshToken(now))
            .build();
    }

    //AccessToken 발행
    private String createAccessToken(long now, Authentication authentication) {

        //해당 유저의 권한 목록 ex) ROLE_ADMIN, ROLE_CUSTOMER
        String authorities = authentication.getAuthorities().stream()
            .map((GrantedAuthority grantedAuthority) -> grantedAuthority.getAuthority())
            .collect(Collectors.joining(","));

        return Jwts.builder()
            .setSubject(authentication.getName())
            .claim("auth", authorities)
            .setExpiration(new Date(now + ACCESS_TOKEN_EXPIRE_TIME))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
    }

    //RefreshToken 발행, claim 없이 만료시간만 담는다
    private String createRefreshToken(long now) {
        return Jwts.builder()
            .setExpiration(new Date(now + REFRESH_TOKEN_EXPIRE_TIME))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
    }
}
