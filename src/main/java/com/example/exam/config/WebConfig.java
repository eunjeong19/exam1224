package com.example.exam.config;

import com.example.exam.interceptor.LoginCheckInterceptor;
import com.example.exam.interceptor.UserInjectInterceptor;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final LoginCheckInterceptor loginCheckInterceptor;
    private final UserInjectInterceptor userInjectInterceptor;

    @PostConstruct
    public void init(){
        log.info("WebConfig 로딩");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        log.info("addInterceptors 실행");
        registry.addInterceptor(loginCheckInterceptor) // loginCheckInterceptor를 스프링에 등록
                .order(1)  // 순서
                .addPathPatterns( // 세션 체크 대상
                        "/board/input",
                        "/board/create",
                        "/board/info",
                        "/board/edit",
                        "/member/memberEdit/**",
                        "/member/memberInfo/**",
                        "/comment/commentEdit"
                )
                .excludePathPatterns(
                        "/home", // 제외 자원
                        "/member/memberLogin",
                        "/member/memberLogout",
                        "/member/memberAuth",
                        "/api/**",
                        "/css/**",
                        "/js/**",
                        "/images/**"
                );

        registry.addInterceptor(userInjectInterceptor)
                .order(2)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/css/**",
                        "/js/**",
                        "/images/**"
                );
    }
}




























