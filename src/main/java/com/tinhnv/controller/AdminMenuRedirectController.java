package com.tinhnv.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;
import com.tinhnv.model.donate.Donate;
import com.tinhnv.service.DonationService;
import com.tinhnv.service.EventService;

@Controller
@RequestMapping("/admin")
public class AdminMenuRedirectController {
	
	@Autowired
	EventService event;
	
	@Autowired
	DonationService donate;
	
	Gson gson = new Gson();
	
	@RequestMapping("/tong-quan")
	public String sayHello(ModelMap map) {
		map.addAttribute("page","dashboard");
		return "dashboard";
	}
	
	@RequestMapping("/quan-ly-chuong-trinh")
	public String eventManage(ModelMap map) {
		map.addAttribute("page", "eventManage");
		return "eventManage";
	}
	
	@RequestMapping("/quan-ly-tai-khoan")
	public String accountManage(ModelMap map) {
		map.addAttribute("page", "accountManage");
		return "accountManage";
	}
	
	@RequestMapping("/them-chuong-trinh")
	public String newEvent(ModelMap map) {
		map.addAttribute("page", "newEvent");
		map.addAttribute("eventId", event.createAndGetIdANullEvent());
		return "newEvent";
	}
	
	@RequestMapping("/them-tai-khoan")
	public String newAccount(ModelMap map) {
		map.addAttribute("page", "newAccount");
		return "newAccount";
	}
	
	@RequestMapping(value={"/dong-thoi-gian"}, method=RequestMethod.GET)
	public String timeline(@RequestParam(name="eventId", required=false, defaultValue="0") int eventId, ModelMap map) {
		map.addAttribute("listEvent", event.getEventBySortAndFilter("lastest", "all"));
		map.addAttribute("eventSelected", eventId);
		map.addAttribute("page", "timeline");
		return "timeline";
	}
	
	//Phằn bổ sung cho donation manage controller --- timeline page

	@RequestMapping(value={"/so-luong-truy-van"}, method=RequestMethod.POST)
	public void totalDonateAction(@RequestParam("isSearching") boolean isSearching,
			@RequestParam("eventId") int eventId, @RequestParam(name="searchValue", required=false) String searchValue,
			HttpServletResponse response) throws IOException {
		int result = donate.getTotalDonateBy(eventId, isSearching, searchValue);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(gson.toJson(result));
	}
	
	@RequestMapping(value={"/danh-sach-dong-gop"}, method=RequestMethod.POST)
	public void listDonateAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<Donate> list = donate.getListDonateForTimeline(request);
		String json = gson.toJson(list);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
	}
}