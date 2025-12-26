package com.example.exam.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        log.info("preHandle 실행, uri={}", request.getRequestURI());

        HttpSession session = request.getSession(false);
        //request.getSession(false) : 이미 세션이 있으면 가져오고, 없으면 새로 만들지 말라는 의미
        // 로그인 체크에서는 반드시 false를 써야 한다.

        if(session == null || session.getAttribute("User") == null){
            response.sendRedirect("/member/memberLogin?msg=required&link="+request.getRequestURI());
            // member/memberLogin으로 리다이렉트시 이전 페이지 link 추가할 경우
            return false; // 컨트롤러 진입 차단
        }
        return true;  // 컨트롤러 실행 허용
    }
}
