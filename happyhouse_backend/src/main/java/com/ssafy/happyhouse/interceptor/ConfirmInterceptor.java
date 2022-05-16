package com.ssafy.happyhouse.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ssafy.happyhouse.model.Member;

import lombok.extern.slf4j.Slf4j;

@Component
@SuppressWarnings("deprecation")
@Slf4j
public class ConfirmInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		Member loginmember = (Member) session.getAttribute("loginmember");
		log.debug("loginmember 세션확인 : {}", loginmember);
		if(loginmember == null) {
//			response.sendRedirect(request.getContextPath() + "/user/login");
			log.debug("로그인값 없음");
			return false;
		}
		return true;
	}
	
}
