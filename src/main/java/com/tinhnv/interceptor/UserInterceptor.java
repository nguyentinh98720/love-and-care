package com.tinhnv.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class UserInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession(true);
		if(session.getAttribute("user") == null) {
			if(request.getMethod().equalsIgnoreCase("post")) {
				response.setCharacterEncoding("UTF-8");
				response.setContentType("text/plain");
				response.getWriter().write("*****Phiên làm việc bị gián đoạn. Bạn cần đăng nhập lại*****");
			} else {
				request.setAttribute("message", "Bạn cần đăng nhập để tiếp tục!");
				request.getRequestDispatcher("/greeting/dang-nhap").forward(request, response);
			}
			return false;
		}
		return true;
	}

}
