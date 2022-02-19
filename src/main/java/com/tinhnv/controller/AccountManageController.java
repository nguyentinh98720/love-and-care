package com.tinhnv.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.google.gson.Gson;
import com.tinhnv.model.account.AccountForIntroduce;
import com.tinhnv.model.account.AccountForManage;
import com.tinhnv.model.others.AccountStatistics;
import com.tinhnv.model.others.ImgSource;
import com.tinhnv.service.AccountService;
import com.tinhnv.service.DonationService;

@Controller
@RequestMapping("/tai-khoan")
public class AccountManageController {

	@Autowired
	AccountService account;
	
	@Autowired
	DonationService donation;

	private Gson gson = new Gson();

	@RequestMapping(value={"/loc/{role}/{status}"}, method=RequestMethod.POST)
	public void getListForMangeByFilter(@PathVariable(name="role") String role, @PathVariable(name="status") String status, HttpServletResponse response) throws IOException {
		List<AccountForManage> list = account.getListForManageByFilter(role, status);
		String json = gson.toJson(list);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
	}
	
	@RequestMapping(value={"/tim-kiem/{condition}/{search}"}, method=RequestMethod.POST)
	public void getListForManageBySearch(@PathVariable(name="condition") String condition, @PathVariable(name="search") String search, HttpServletResponse response) throws IOException {
		List<AccountForManage> list = account.getListForManageBySearch(condition, search);
		String json = gson.toJson(list);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
	}
	
	@RequestMapping(value={"/xoa-tai-khoan"}, method=RequestMethod.POST)
	public void deleteAccount(@RequestParam(name="id") int id, HttpServletResponse response) throws IOException {
		boolean result = account.deleteAccount(id);
		String json = gson.toJson(result);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
	}
	
	@RequestMapping(value={"/thay-doi-trang-thai"}, method=RequestMethod.POST)
	public void changerStatus(@RequestParam(name="id") int id, @RequestParam(name="status") boolean status, HttpServletResponse response) throws IOException {
		boolean result = account.changeStatus(id, status);
		String json = gson.toJson(result);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
	}
	
	@RequestMapping(value={"/cap-nhat-tai-khoan"}, method=RequestMethod.GET)
	public ModelAndView editAccountInformantion(@RequestParam(name="accountId") int id) {
		ModelAndView view = new ModelAndView("editAccount");
		AccountForManage anAccount = account.getAccountForEdit(id);
		view.addObject("account", anAccount);
		return view;
	}
	
	@RequestMapping(value={"/hoan-thanh-cap-nhat"}, method=RequestMethod.POST)
	public RedirectView updateAccountInformation(@RequestPart(name="imgAccount") MultipartFile image, HttpServletRequest request) throws IOException {
		boolean result = account.updateAccountInformation(request, image);
		RedirectView view = new RedirectView("https://love-and-care.herokuapp.com/admin/quan-ly-tai-khoan");
		view.addStaticAttribute("resultUpdate", result);
		return view;
	}
	
	@RequestMapping(value={"/xoa-anh-dai-dien"}, method=RequestMethod.POST)
	public void deleteAccountAvatar(@RequestParam(name="id") int id, HttpServletResponse response) throws IOException {
		boolean result = account.deleteAccountAvatar(id);
		String json = gson.toJson(result);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
	}
	
	@RequestMapping(value={"/kiem-tra-email"}, method=RequestMethod.POST)
	public void checkExistEmail(@RequestParam(name="email") String email, HttpServletResponse response) throws IOException {
		boolean result = account.isNewEmail(email);
		String json = gson.toJson(result);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
	}
	
	@RequestMapping(value={"/them-tai-khoan-moi"}, method=RequestMethod.POST)
	public RedirectView createNewAccount(HttpServletRequest request, @RequestParam(name="imgAccount") MultipartFile image) throws IOException {
		boolean result = account.createNewAccount(request, image);
		RedirectView view = new RedirectView("https://love-and-care.herokuapp.com/admin/quan-ly-tai-khoan");
		view.addStaticAttribute("resultCreate", result);
		return view;
	}
	
	@RequestMapping(value={"/thong-tin-tai-khoan"}, method=RequestMethod.GET)
	public ModelAndView detailAccount(@RequestParam(name="accountId") int id) {
		ModelAndView view = new ModelAndView("detailAccount");
		AccountForIntroduce result = account.getIntroduceAccount(id);
		view.addObject("account",result);
		return view;
	}
	
	@RequestMapping(value={"/reset-mat-khau"}, method=RequestMethod.POST)
	public void resetAccountPassword(@RequestParam(name="id") int userId, @RequestParam(name="email") String userEmail, HttpServletResponse response) throws IOException {
		boolean result = false;
		result = account.resetAccountPassword(userId, userEmail);
		String json = gson.toJson(result);
		response.getWriter().write(json);
	}
	
	@RequestMapping(value={"/thong-ke-tai-khoan"}, method=RequestMethod.POST)
	public void accountStatistics(HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		AccountStatistics statistics = account.getAccountStatistics();
		String json = gson.toJson(statistics);
		response.getWriter().write(json);
	}

	@RequestMapping(value={"/avatar"}, method=RequestMethod.GET)
	public void accountAvatar(@RequestParam("accountId") int accountId, HttpServletResponse response) throws IOException {
		ImgSource result = account.getAccountAvatar(accountId);
		if(result.getArr() != null) {
			response.setContentType(result.getType());
			response.setContentLength(result.getArr().length);
			response.getOutputStream().write(result.getArr());
		}
	}
}
