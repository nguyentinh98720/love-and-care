package com.tinhnv.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tinhnv.dao.EventDao;
import com.tinhnv.model.donate.Donate;
import com.tinhnv.model.event.EventForEdit;
import com.tinhnv.model.event.EventForManage;
import com.tinhnv.model.event.EventFullInfo;
import com.tinhnv.model.others.ImgEvent;
import com.tinhnv.model.others.PieChartData;
import com.tinhnv.model.others.ResponseList;
import com.tinhnv.service.EventService;

@Service
public class EventServiceimpl implements EventService {

	@Autowired
	EventDao eventAction;
	
	@Override
	public List<EventForManage> getEventBySortAndFilter(String sort, String filter) {
		return eventAction.getListEventManage(sort, filter);
	}

	@Override
	public List<EventForManage> getEventBySearch(String search) {
		return eventAction.getListEventManage(search);
	}

	@Override
	public ResponseList getEventToHomePage(int from, int to, boolean only_active) {
		return eventAction.getListEventHomePage(from, to, only_active);
	}

	@Override
	public ResponseList getEventToSearchPage(int from, int to, boolean only_active, String searchValue) {
		return eventAction.getListEventSearchPage(from, to, only_active, searchValue);
	}

	@Override
	public boolean setEventStatus(int id, boolean status) {
		if(eventAction.setEventStatus(id, status) > 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteEvent(int id) {
		if(eventAction.deleteEvent(id) > 0) {
			return true;
		}
		return false;
	}

	@Override
	public EventForEdit getDetailEvent(int id) {
		return eventAction.getDetailEvent(id);
	}

	@Override
	public List<ImgEvent> getImageEvent(int eventId) {
		return eventAction.getImageEvent(eventId);
	}

	@Override
	public ImgEvent getImageEventById(int imageId) {
		return eventAction.getImageEventById(imageId);
	}

	@Override
	public boolean deleteImageEvent(int id) {
		if(eventAction.deleteImageEvent(id) > 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean updateEvent(HttpServletRequest request, MultipartFile[] files) {
		int eventId = Integer.parseInt(request.getParameter("eventId"));
		String title = request.getParameter("title");
		Date startDate = Date.valueOf(request.getParameter("startDate"));
		String date = request.getParameter("endDate");
		Date endDate = null;
		if(!date.equals("") && date != null) {
			endDate = Date.valueOf(date);
		}
		boolean status = Boolean.valueOf(request.getParameter("status"));
		BigDecimal purpose = new BigDecimal(request.getParameter("purpose"));
		String detail = request.getParameter("detail");
		
		EventForEdit event = new EventForEdit(eventId, title.trim(), startDate, endDate, purpose, new BigDecimal(0), status, detail.trim());
		eventAction.updateDetailEvent(event);
		
		List<ImgEvent> imgs = new ArrayList<>();
		try {
			for(MultipartFile image : files) {
				if(image.getName().equals("images")) {
					imgs.add(new ImgEvent(image.getInputStream().readAllBytes(), image.getContentType(), eventId));
				}
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
		if(imgs.size() > 0) {
			eventAction.saveImageEvent(imgs);
		}
		return true;
	}

	@Override
	public boolean insertEvent(HttpServletRequest request, MultipartFile[] files) {
		String title = request.getParameter("title");
		Date startDate = Date.valueOf(request.getParameter("startDate"));
		String date = request.getParameter("endDate");
		Date endDate = null;
		if(!date.equals("") && date != null) {
			endDate = Date.valueOf(date);
		}
		boolean status = Boolean.valueOf(request.getParameter("status"));
		BigDecimal purpose = new BigDecimal(request.getParameter("purpose"));
		String detail = request.getParameter("detail");
		
		EventForEdit event = new EventForEdit(0, title, startDate, endDate, purpose, new BigDecimal(0), status, detail);
		int eventId = eventAction.insertDetailEvent(event);
		
		List<ImgEvent> imgs = new ArrayList<>();
		try {
			for(MultipartFile image : files) {
				if(image.getName().equals("images")) {
					imgs.add(new ImgEvent(image.getInputStream().readAllBytes(), image.getContentType(), eventId));
				}
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
		if(imgs.size() > 0) {
			eventAction.saveImageEvent(imgs);
		}
		return true;
	}

	@Override
	public EventFullInfo getDonation(int id) {
		long count = eventAction.getDonationCount(id);
		EventForEdit event = eventAction.getDetailEvent(id);
		List<ImgEvent> images = eventAction.getImageEvent(id);
		List<Donate> topDonate = eventAction.getTopDonate(id);
		List<Donate> topRichDonate = eventAction.getTopRichDonate(id);
		EventFullInfo donation = new EventFullInfo(event);
		
		donation.setImages(images);
		donation.setDonationCount(count);
		donation.setTopDonate(topDonate);
		donation.setRichDonate(topRichDonate);
		
		return donation;
	}

	@Override
	public PieChartData pieChartData() {
		PieChartData pie = new PieChartData();
		pie.setRunning(eventAction.runningEvent());
		pie.setStopping(eventAction.stoppingEvent());
		pie.setEnding(eventAction.endingEvent());
		return pie;
	}

	@Override
	public List<BigDecimal> getDataForLineChart(int eventId, int length) {
		return eventAction.getDataForLineChart(eventId, length);
	}

	@Override
	public Map<String,EventForManage> getSpecEvent() {
		EventForManage shortestTimeDeployment = eventAction.eventHasShortestTimeDeployment();
		EventForManage highestPurpose = eventAction.eventHasHighestPurpose();
		Map<String,EventForManage> map = new HashMap<>();
		map.put("highestPurpose", highestPurpose);
		map.put("shortestTimeDeployment", shortestTimeDeployment);
		return map;
	}

	@Override
	public int numEventNoTimeEnd() {
		return eventAction.noTimeEndEvent();
	}

	@Override
	public int createAndGetIdANullEvent() {
		return eventAction.createAndGetIdANullEvent();
	}

	@Override
	public int saveImageAndGetId(MultipartFile image, int eventId) throws IOException {
		ImgEvent img = new ImgEvent(image.getBytes(), image.getContentType(), 0);
		img.setEventId(eventId);
		return eventAction.saveAndGetIdImageEvent(img);
	}

	@Override
	public boolean updateEventPart2(HttpServletRequest request) {
		int eventId = Integer.parseInt(request.getParameter("eventId"));
		String title = request.getParameter("title");
		Date startDate = Date.valueOf(request.getParameter("startDate"));
		String date = request.getParameter("endDate");
		Date endDate = null;
		if(!date.equals("") && date != null) {
			endDate = Date.valueOf(date);
		}
		BigDecimal purpose = new BigDecimal(request.getParameter("purpose"));
		String detail = request.getParameter("detail");
		boolean status = true;
		String statusStr = request.getParameter("status");
		if(statusStr != null) {
			status = Boolean.valueOf(statusStr);
		}
		EventForEdit event = new EventForEdit(eventId, title.trim(), startDate, endDate, purpose, new BigDecimal(0), status, detail.trim());
		eventAction.updateDetailEvent(event);
		return true;
	}

	@Override
	public String getEventTitle(int eventId) {
		return eventAction.eventTile(eventId);
	}
}
