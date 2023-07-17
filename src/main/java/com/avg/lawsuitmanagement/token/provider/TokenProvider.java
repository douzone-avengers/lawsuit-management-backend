package com.avg.lawsuitmanagement.token.provider;


import com.avg.lawsuitmanagement.token.dto.JwtTokenDto;
import com.avg.lawsuitmanagement.member.dto.MemberDto;
import com.avg.lawsuitmanagement.token.service.CustomUserDetail;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TokenProvider {

    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 5; //5분
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

    /*
    토큰이 적법한지 검증한다.
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return false;
    }

    public Authentication getAuthentication(String accessToken) {
        // 토큰 복호화
        Claims claims = parseClaims(accessToken);

        if (claims.get("auth") == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        // 클레임에서 권한 정보 가져오기

//        String[] authorityArr = claims.get("auth").toString().split(",");
//        List<GrantedAuthority> authorityList = new ArrayList<>();
//        for(String auth : authorityArr) {
//            authorityList.add(new SimpleGrantedAuthority(auth));
//        }

        List<GrantedAuthority> authorityList =
            Arrays.stream(claims.get("auth").toString().split(","))
                .map((String authority) -> new SimpleGrantedAuthority(authority))
                .collect(Collectors.toList());

        // UserDetails 객체를 만들어서 Authentication 리턴

        MemberDto memberDto = MemberDto.builder()
            .name(claims.getSubject())
            .build();

        CustomUserDetail customUserDetail = new CustomUserDetail(memberDto);

        return new UsernamePasswordAuthenticationToken(customUserDetail, "", authorityList);
    }


    //AccessToken 발행
    private String createAccessToken(long now, Authentication authentication) {

        //해당 유저의 권한 목록 ex) ROLE_ADMIN, ROLE_EMPLOYEE

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

    private Claims parseClaims(String accessToken) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken)
            .getBody();
    }
}
