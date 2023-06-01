package com.multi.board.multiboard.config;

import com.multi.board.multiboard.security.MemberSecurityService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig { // 그냥 하게 되면 모두 로그인 처리, 사용자 custom으로 변경

    private final MemberSecurityService memberSecurityService;

    private static final String[] AUTH_WHITELIST = { // 인증 제외
            "/", "/static/**", "/member/checkId","/member/checkEmail"};

    private static final String[] AUTH_ANONYMOUSLIST = { // 인증 제외
            "/member/register","/member/login"};

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

//        http.csrf().disable();
        http.userDetailsService(memberSecurityService);
        http
                .csrf((csrf) -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(AUTH_ANONYMOUSLIST).anonymous()
                        .requestMatchers(AUTH_WHITELIST).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login.loginPage("/member/login")
//						.defaultSuccessUrl("/")
//						.failureUrl("/member/login.html?error=true")
                        .usernameParameter("id")
                        .passwordParameter("password")
                        .successHandler((request, response, authentication) -> {	// 인증 성공시 처리내용
                            String userName = authentication.getName();
                            String redirectUrl = "/";
                            String javascriptCode = "alert('" + userName + "님, 로그인에 성공하였습니다.'); window.location.href = '" + redirectUrl + "';";

                            response.setStatus(HttpServletResponse.SC_OK);
                            response.setContentType("text/html");
                            response.setCharacterEncoding("UTF-8");
                            response.getWriter().write("<script>" + javascriptCode + "</script>");
                        })
                        .failureHandler((request, response, exception) -> {
                            String redirectUrl = "/member/login";
                            String javascriptCode = "alert('로그인에 실패했습니다.'); window.location.href = '" + redirectUrl + "';";
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 상태 코드 401(Unauthorized) 설정
                            response.setContentType("text/html");
                            response.setCharacterEncoding("UTF-8");
                            response.getWriter().write("<script>" + javascriptCode + "</script>");
                        })
                )
                .logout((logout) ->
                        logout.deleteCookies("remove")
                                .invalidateHttpSession(true)
                                .logoutUrl("/member/logout")
                                .logoutSuccessUrl("/member/login")
                );
        return http.build();
    }

    @Bean
    protected WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations()); // 정적 리소스 인증 무시
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
