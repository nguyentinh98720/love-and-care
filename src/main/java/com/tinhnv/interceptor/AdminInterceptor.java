package com.tinhnv.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.tinhnv.model.account.SessionUser;
import com.tinhnv.setting.AccountConfig;

@Component
public class AdminInterceptor extends HandlerInterceptorAdapter {
	
	@Autowired
	AccountConfig accountConfig;

	private Logger logger = Logger.getLogger(AdminInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession(false);
		if(session == null) {
			logger.warn("Already blocked a null session's access");
			response.sendRedirect("https://love-and-care.herokuapp.com/greeting/dang-nhap");
			return false;
		}
		if(session.getAttribute("user") == null) {
			logger.warn("a Null user session accessing admin page has been blocked!");
			if(request.getMethod().equalsIgnoreCase("post")) {
				response.setCharacterEncoding("UTF-8");
				response.setContentType("text/plain");
				response.getWriter().write("*****Phiên làm việc bị gián đoạn. Bạn cần đăng nhập lại*****");
			} else {
				response.sendRedirect("https://love-and-care.herokuapp.com/greeting/dang-nhap");
			}
			return false;
		}
		SessionUser user = (SessionUser) session.getAttribute("user");
		String role = user.getRole();
		if(!role.equalsIgnoreCase(accountConfig.ADMIN)) {
			logger.warn("Block a ROLE USER's accessing to admin page!");
			response.sendRedirect("https://love-and-care.herokuapp.com/greeting/home");
			return false;
		}
		logger.info("admin accepted!");
		return true;
	}
}
