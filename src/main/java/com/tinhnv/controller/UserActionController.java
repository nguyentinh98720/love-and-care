package com.tinhnv.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;
import com.tinhnv.model.account.SessionUser;
import com.tinhnv.model.others.ImgEvent;
import com.tinhnv.model.others.ResponseList;
import com.tinhnv.service.AccountService;
import com.tinhnv.service.EventService;
import com.tinhnv.setting.AccountConfig;

@Controller
@RequestMapping("/action")
public class UserActionController {
	
	@Autowired
	EventService event;
	
	@Autowired
	AccountService account;
	
	@Autowired
	AccountConfig accountConfig;
	
	private Gson gson = new Gson();

	@RequestMapping(value={"/lay-danh-sach"}, method=RequestMethod.POST)
	public void getListEvent(@RequestParam(name="from") int from, @RequestParam(name="to") int to,
			@RequestParam(name="only_active") Boolean only_active, HttpServletResponse response) throws IOException {
		ResponseList list = event.getEventToHomePage(from, to, only_active);
		String json = gson.toJson(list);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
	}
	
	@RequestMapping(value={"/glogin", "/fblogin"}, method=RequestMethod.POST)
	public void googleLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String email = request.getParameter("email");
		boolean isNew = account.isNewEmail(email);
		if(isNew) {
			account.createAccountFromRegister_NotSendEmail(request);
		}
		HttpSession session = request.getSession(true);
		SessionUser user = account.checkAndGetAccountForGoogleLogin(request);
		session.setAttribute("user", user);
		session.setMaxInactiveInterval(180);
		boolean accessable = user.isActive();
		String url = "null";
		if(user.getRole().equals(accountConfig.ADMIN) && accessable) {
			//admin dashboard
			url = "https://love-and-care.herokuapp.com/admin/tong-quan";
		} else if(user.getRole().equals(accountConfig.USER) && accessable) {
			//user home page
			url = "https://love-and-care.herokuapp.com/greeting/home";
		}
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(url);
	}
	
	@RequestMapping(value={"/ck-lay-hinh-anh"}, method=RequestMethod.GET)
	public void getImg(@RequestParam("imageId") int imageId, HttpServletResponse response) throws IOException {
		ImgEvent img = event.getImageEventById(imageId);
		response.setContentType(img.getType());
		response.setContentLength(img.getArr().length);
		response.getOutputStream().write(img.getArr());
	}
	
	@RequestMapping(value={"/tim-kiem-chuong-trinh"}, method=RequestMethod.GET)
	public String searchResultPage(HttpServletRequest request) {
		request.setAttribute("searchValue", request.getParameter("titleSearch"));
		return "search";
	}
	
	@RequestMapping(value={"/tim-kiem-chuong-trinh"}, method=RequestMethod.POST)
	public void getListEventForSearch(@RequestParam(name="from") int from, @RequestParam(name="to") int to,
			@RequestParam(name="only_active") Boolean only_active, @RequestParam(name="searchValue") String searchValue,
			HttpServletResponse response) throws IOException {
		ResponseList list = event.getEventToSearchPage(from, to, only_active, searchValue.trim());
		String json = gson.toJson(list);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
	}
}
