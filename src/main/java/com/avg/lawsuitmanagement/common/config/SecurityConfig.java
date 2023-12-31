package com.avg.lawsuitmanagement.common.config;


import com.avg.lawsuitmanagement.common.custom.CustomAccessDeniedHandler;
import com.avg.lawsuitmanagement.common.custom.CustomAuthenticationEntryPoint;
import com.avg.lawsuitmanagement.token.provider.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final TokenProvider tokenProvider;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
            .httpBasic().disable()
            .csrf().disable() //세션 사용하지 않으므로, csrf 보안 정책 필요x
            .cors()

            .and()
            .authorizeRequests()
            .antMatchers(HttpMethod.POST, "/promotions/clients").hasRole("EMPLOYEE")
            .antMatchers(HttpMethod.GET, "/members/employees").hasRole("EMPLOYEE")
            .antMatchers("/statistics/**").hasRole("EMPLOYEE")
            .antMatchers(HttpMethod.POST, "/promotions/employees").hasRole("ADMIN")
            .antMatchers(HttpMethod.PUT, "/members/employees/**").hasRole("ADMIN")
            .antMatchers(HttpMethod.PATCH, "/members/employees/**").hasRole("ADMIN")
            .antMatchers("/emails/**").hasRole("EMPLOYEE")

            .antMatchers("/tokens/**", "/test/**", "/hierarchy/**", "/role/**", "/court/**" ).permitAll()
            .antMatchers(HttpMethod.GET, "/promotions/clients", "/promotions/employees").permitAll()
            .antMatchers(HttpMethod.POST, "/members/clients", "/members/employees").permitAll()
            .antMatchers(HttpMethod.GET, "/health-check").permitAll()
            .antMatchers("/socket.io/**").permitAll()

            .anyRequest().authenticated() //나머지 요청은 인증 필요

            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

            //인증 간 예외처리
            .and()
            .exceptionHandling()
            .authenticationEntryPoint(customAuthenticationEntryPoint)
            //인가 예외처리
            .accessDeniedHandler(customAccessDeniedHandler)

            .and()
            .addFilterBefore(new JwtFilter(tokenProvider),
                UsernamePasswordAuthenticationFilter.class)

            .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.addAllowedOriginPattern("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

}
