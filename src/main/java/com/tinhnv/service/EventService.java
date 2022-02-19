package com.tinhnv.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.tinhnv.model.event.EventForEdit;
import com.tinhnv.model.event.EventForManage;
import com.tinhnv.model.event.EventFullInfo;
import com.tinhnv.model.others.ImgEvent;
import com.tinhnv.model.others.PieChartData;
import com.tinhnv.model.others.ResponseList;

public interface EventService {
	
	List<EventForManage> getEventBySortAndFilter(String sort, String filter);
	List<EventForManage> getEventBySearch(String search);
	ResponseList getEventToHomePage(int from, int to, boolean only_active);
	ResponseList getEventToSearchPage(int from, int to, boolean only_active, String searchValue);
	
	boolean setEventStatus(int id, boolean status);
	boolean deleteEvent(int id);

	EventForEdit getDetailEvent(int id);
	List<ImgEvent> getImageEvent(int eventId);
	ImgEvent getImageEventById(int imageId);
	boolean deleteImageEvent(int id);
	
	boolean updateEvent(HttpServletRequest request, MultipartFile[] files);
	boolean insertEvent(HttpServletRequest request, MultipartFile[] files);
	
	EventFullInfo getDonation(int id);
	
	PieChartData pieChartData();
	List<BigDecimal> getDataForLineChart(int eventId, int length);
	
	Map<String,EventForManage> getSpecEvent();
	int numEventNoTimeEnd();
	
	int createAndGetIdANullEvent();
	int saveImageAndGetId(MultipartFile image, int eventId) throws IOException;
	boolean updateEventPart2(HttpServletRequest request);
	
	String getEventTitle(int eventId);
}
