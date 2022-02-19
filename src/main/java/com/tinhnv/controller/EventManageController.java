package com.tinhnv.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.google.gson.Gson;
import com.tinhnv.model.event.EventForManage;
import com.tinhnv.model.event.EventFullInfo;
import com.tinhnv.model.others.ImageResponse;
import com.tinhnv.model.others.ImgEvent;
import com.tinhnv.model.others.PieChartData;
import com.tinhnv.service.EventService;

@Controller
@RequestMapping(value={"/chuong-trinh"})
public class EventManageController {
	
	@Autowired
	EventService event;
	Gson gson = new Gson();
	
	@RequestMapping(value={"/danh-sach-chuong-trinh/{sort}/{filter}"}, method=RequestMethod.POST)
	@ResponseBody
	public void getListSortAndFilter(HttpServletResponse response, @PathVariable(name="sort") String sort,
			@PathVariable(name="filter") String filter) throws IOException {
		List<EventForManage> list = event.getEventBySortAndFilter(sort, filter);
		String json = gson.toJson(list);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset:utf-8");
		response.getWriter().write(json);
	}

	@RequestMapping(value={"/tim-kiem-chuong-trinh/{search}"}, method=RequestMethod.POST)
	@ResponseBody
	public void getListSearch(HttpServletResponse response, @PathVariable(name="search") String search) throws IOException {
		List<EventForManage> list = event.getEventBySearch(search);
		String json = gson.toJson(list);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset:utf-8");
		response.getWriter().write(json);
	}

	@RequestMapping(value={"/cai-dat-trang-thai"}, method=RequestMethod.POST)
	@ResponseBody
	public void setStatusEvent(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		boolean status = Boolean.valueOf(request.getParameter("status"));
		Boolean success = event.setEventStatus(id, status);
		String json = gson.toJson(success);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset:utf-8");
		response.getWriter().write(json);
	}

	@RequestMapping(value={"/xoa-chuong-trinh"}, method=RequestMethod.POST)
	@ResponseBody
	public void deleteEvent(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		Boolean success = event.deleteEvent(id);
		String json = gson.toJson(success);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset:utf-8");
		response.getWriter().write(json);
	}
	
	@RequestMapping(value={"/cap-nhat-chuong-trinh"}, method=RequestMethod.GET)
	public ModelAndView editEvent(@RequestParam(name="eventId", required=true) int id) {
		ModelAndView mav = new ModelAndView("editEvent");
		mav.addObject("event", event.getDetailEvent(id));
		return mav;
	}
	
	@RequestMapping(value={"/anh-chuong-trinh"}, method=RequestMethod.POST)
	public void getImageForEvent(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int eventId = Integer.parseInt(request.getParameter("eventId"));
		List<ImgEvent> list = event.getImageEvent(eventId);
		String json = gson.toJson(list);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset:utf-8");
		response.getWriter().write(json);
	}
	
	@RequestMapping(value={"/xoa-anh-chuong-trinh"}, method=RequestMethod.POST)
	public void deleteEventImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		Boolean success = event.deleteImageEvent(id);
		String json = gson.toJson(success);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset:utf-8");
		response.getWriter().write(json);
	}
	
	@RequestMapping(value={"/hoan-thanh-cap-nhat"}, method=RequestMethod.POST)
	public RedirectView updateEvent(HttpServletRequest request, @RequestParam(name="images") MultipartFile[] images) {
		boolean resultUpdate = event.updateEvent(request, images);
		RedirectView view = new RedirectView("https://love-and-care.herokuapp.com/admin/quan-ly-chuong-trinh");
		view.addStaticAttribute("resultUpdate", resultUpdate);
		return view;
	}
	
	@RequestMapping(value={"/ck-hoan-thanh-cap-nhat"}, method=RequestMethod.POST)
	public RedirectView updateEventWithCKEditor(HttpServletRequest request) {
		boolean resultUpdate = event.updateEventPart2(request);
		RedirectView view = new RedirectView("https://love-and-care.herokuapp.com/admin/quan-ly-chuong-trinh");
		view.addStaticAttribute("resultUpdate", resultUpdate);
		return view;
	}
	
	@RequestMapping(value={"/them-chuong-trinh"}, method=RequestMethod.POST)
	public RedirectView createEvent(HttpServletRequest request, @RequestParam(name="images") MultipartFile[] images) {
		boolean resultCreate = event.insertEvent(request, images);
		RedirectView view = new RedirectView("https://love-and-care.herokuapp.com/admin/quan-ly-chuong-trinh");
		view.addStaticAttribute("resultCreate", resultCreate);
		return view;
	}
	
	@RequestMapping(value={"/ck-them-chuong-trinh"}, method=RequestMethod.POST)
	public RedirectView createEventWithCKEditor(HttpServletRequest request) {
		boolean resultCreate = event.updateEventPart2(request);
		RedirectView view = new RedirectView("https://love-and-care.herokuapp.com/admin/quan-ly-chuong-trinh");
		view.addStaticAttribute("resultCreate", resultCreate);
		return view;
	}
	
	@RequestMapping(value={"/chi-tiet-chuong-trinh/{id}"}, method=RequestMethod.GET)
	public ModelAndView seeDetailDonation(@PathVariable(name="id") int id) {
		EventFullInfo donation = event.getDonation(id);
		ModelAndView view = new ModelAndView("detailEvent");
		view.addObject("donation", donation);
		return view;
	}
	
	@RequestMapping(value={"/pie-chart"}, method=RequestMethod.POST)
	public void dataForPieChar(HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PieChartData pie = event.pieChartData();
		String json = gson.toJson(pie);
		response.getWriter().write(json);
	}
	
	@RequestMapping(value={"/line-chart-list"}, method=RequestMethod.POST)
	public void dataForLineChart1(HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		List<EventForManage> list = event.getEventBySortAndFilter("lastest", "running");
		String json = gson.toJson(list);
		response.getWriter().write(json);
	}
	
	@RequestMapping(value={"/line-chart-statistics"}, method=RequestMethod.POST)
	public void dataForLineChar2(@RequestParam(name="eventId") int eventId,
			@RequestParam(name="length") int length, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		List<BigDecimal> list = event.getDataForLineChart(eventId, length);
		String json = gson.toJson(list);
		response.getWriter().write(json);
	}

	@RequestMapping(value={"/chuong-trinh-dac-biet"}, method=RequestMethod.POST)
	public void getSpecialEvent1(HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		Map<String, EventForManage> result = event.getSpecEvent();
		String json = gson.toJson(result);
		response.getWriter().write(json);
	}
	
	@RequestMapping(value={"/khong-thoi-han"}, method=RequestMethod.POST)
	public void getSpecialEvent2(HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		int result = event.numEventNoTimeEnd();
		String json = gson.toJson(result);
		response.getWriter().write(json);
	}
	
	@RequestMapping(value={"/ck-luu-hinh-anh"}, method=RequestMethod.POST)
	public void testLink(@RequestParam("upload") MultipartFile file, @RequestParam(name="eventId") int eventId,
			HttpServletResponse response) throws IOException {
		int imageId = event.saveImageAndGetId(file, eventId);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		String url = "https://love-and-care.herokuapp.com/action/ck-lay-hinh-anh?imageId=" + gson.toJson(imageId);
		ImageResponse imgRes = new ImageResponse(true, url, null);
		response.getWriter().write(gson.toJson(imgRes));
	}
	
	@RequestMapping(value={"/ck-lay-hinh-anh"}, method=RequestMethod.GET)
	public void getImg(@RequestParam("imageId") int imageId, HttpServletResponse response) throws IOException {
		ImgEvent img = event.getImageEventById(imageId);
		response.setContentType(img.getType());
		response.setContentLength(img.getArr().length);
		response.getOutputStream().write(img.getArr());
	}
	
	@RequestMapping(value={"/tieu-de-chuong-trinh"})
	public void getTitle(@RequestParam("eventId") int eventId, HttpServletResponse response) throws IOException {
		String result = event.getEventTitle(eventId);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(gson.toJson(result));
	}
}
