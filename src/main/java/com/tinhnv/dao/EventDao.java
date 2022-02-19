package com.tinhnv.dao;

import java.math.BigDecimal;
import java.util.List;

import com.tinhnv.model.donate.Donate;
import com.tinhnv.model.event.EventForEdit;
import com.tinhnv.model.event.EventForManage;
import com.tinhnv.model.others.ImgEvent;
import com.tinhnv.model.others.ResponseList;
import com.tinhnv.model.others.VaTiImage;

public interface EventDao {
	
	List<EventForManage> getListEventManage();
	List<EventForManage> getListEventManage(String sort, String filter);
	List<EventForManage> getListEventManage(String search);
	ResponseList getListEventHomePage(int from, int to, boolean only_active);
	ResponseList getListEventSearchPage(int from, int to, boolean only_active, String searchValue);
	
	int setEventStatus(int id, boolean status);
	
	int deleteEvent(int id);
	
	EventForEdit getDetailEvent(int id);
	List<ImgEvent> getImageEvent(int eventId);
	ImgEvent getImageEventById(int imageId);
	VaTiImage getImageBannerForEvent(int eventId);
	int getNumberDonateActionPerEvent(int eventId);
	int deleteImageEvent(int id);
	
	int updateDetailEvent(EventForEdit event);
	int insertDetailEvent(EventForEdit event);
	int createAndGetIdANullEvent();
	int saveAndGetIdImageEvent(ImgEvent img);
	int saveImageEvent(List<ImgEvent> imgs);
	int deleteAllImageEvent(int eventId);
	
	long getDonationCount(int id);
	List<Donate> getTopDonate(int eventId);
	List<Donate> getTopRichDonate(int eventId);
	
	int runningEvent();
	int stoppingEvent();
	int endingEvent();
	int noTimeEndEvent();
	
	List<BigDecimal> getDataForLineChart(int eventId, int length);
	
	EventForManage eventHasHighestPurpose();
	EventForManage eventHasShortestTimeDeployment();
	
	String eventTile(int eventId);
}
