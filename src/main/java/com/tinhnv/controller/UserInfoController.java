package com.tinhnv.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.tinhnv.model.account.AccountForManage;
import com.tinhnv.model.account.SessionUser;
import com.tinhnv.model.donate.Donate;
import com.tinhnv.model.others.DonateStatistics;
import com.tinhnv.service.AccountService;
import com.tinhnv.service.DonationService;

@Controller
@RequestMapping("/quan-ly")
public class UserInfoController {
	
	@Autowired
	AccountService account;
	
	@Autowired
	DonationService donation;
	
	Gson gson = new Gson();

	@RequestMapping(value={"/thong-tin-tai-khoan"}, method=RequestMethod.GET)
	public String profilesAccountPage(HttpServletRequest request) {
		int userId = getSessionUserId(request);
		if(userId == 0) return "error";
		AccountForManage profile = account.getAccountForEdit(userId);
		request.setAttribute("profile", profile);
		return "profile";
	}
	
	@RequestMapping(value={"/xoa-anh-dai-dien"}, method=RequestMethod.POST)
	public void deleteAvatar(HttpServletRequest request, HttpServletResponse response) throws IOException {
		boolean success = false;
		int userId = getSessionUserId(request);
		if(userId != 0) {
			success = account.deleteAccountAvatar(userId);
		}
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(gson.toJson(success));
	}
	
	@RequestMapping(value={"/cap-nhat-thong-tin-tai-khoan"}, method=RequestMethod.POST)
	public String updateProfileAccount(HttpServletRequest request,
			@RequestParam(name="avatar", required = false) MultipartFile avatar) throws IOException {
		if(account.updateAccountInformationUserAction(request, avatar)) return "redirect:/greeting/home";
		else return "home";
	}
	
	@RequestMapping(value={"/lich-su-quyen-gop"}, method=RequestMethod.GET)
	public String historyDonatePage(HttpServletRequest request) {
		int userId = getSessionUserId(request);
		if(userId == 0) return "error";
		DonateStatistics statistics = donation.getDonateStatistics(userId);
		request.setAttribute("statistics", statistics);
		return "history";
	}
	
	@RequestMapping(value={"/lich-su-quyen-gop"}, method=RequestMethod.POST)
	public void getDonateHistory(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int userId = getSessionUserId(request);
		List<Donate> history = null;
		if(userId != 0) {
			int from = Integer.parseInt(request.getParameter("from"));
			int to = Integer.parseInt(request.getParameter("to"));
			history = donation.getDonateHistory(from, to, userId);
		}
		String json = gson.toJson(history);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
	}
	
	private int getSessionUserId(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		if(session.getAttribute("user") == null) return 0;
		SessionUser user = (SessionUser) session.getAttribute("user");
		return user.getId();
	}
}
