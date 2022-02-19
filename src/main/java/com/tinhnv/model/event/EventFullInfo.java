package com.tinhnv.model.event;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.tinhnv.model.donate.Donate;
import com.tinhnv.model.others.ImgEvent;

@Component
public class EventFullInfo extends EventForEdit{
	
	private List<ImgEvent> images;
	private long donationCount;
	private List<Donate> topDonate;
	private List<Donate> richDonate;
	
	public EventFullInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EventFullInfo(int id, String title, Date startDate, Date endDate, BigDecimal purpose,
			BigDecimal achievement, boolean status, String detail) {
		super(id, title, startDate, endDate, purpose, achievement, status, detail);
		// TODO Auto-generated constructor stub
	}
	
	public EventFullInfo(EventForEdit event) {
		this.setId(event.getId());
		this.setTitle(event.getTitle());
		this.setStartDate(event.getStartDate());
		this.setEndDate(event.getEndDate());
		this.setPurpose(event.getPurpose());
		this.setAchievement(event.getAchievement());
		this.setStatus(event.getStatus());
		this.setDetail(event.getDetail());
	}

	public List<ImgEvent> getImages() {
		return images;
	}

	public void setImages(List<ImgEvent> images) {
		this.images = images;
	}
	
	public long getDonationCount() {
		return donationCount;
	}
	
	public void setDonationCount(long count) {
		this.donationCount = count;
	}

	public List<Donate> getTopDonate() {
		return topDonate;
	}

	public void setTopDonate(List<Donate> topDonate) {
		this.topDonate = topDonate;
	}

	public List<Donate> getRichDonate() {
		return richDonate;
	}

	public void setRichDonate(List<Donate> lastestDonate) {
		this.richDonate = lastestDonate;
	}
}
