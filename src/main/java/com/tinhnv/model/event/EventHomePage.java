package com.tinhnv.model.event;

import org.springframework.stereotype.Component;

import com.tinhnv.model.others.VaTiImage;

@Component
public class EventHomePage extends EventForManage {

	private VaTiImage image;
	private int numDonates;
	
	public EventHomePage() {
		super();
	}
	
	public EventHomePage(EventForManage event) {
		super(event.getId(), event.getTitle(), event.getStartDate(), event.getEndDate(),
				event.getPurpose(), event.getAchievement(), event.getStatus());
	}
	
	public EventHomePage(EventForManage event, VaTiImage image, int numDonates) {
		super(event.getId(), event.getTitle(), event.getStartDate(), event.getEndDate(),
				event.getPurpose(), event.getAchievement(), event.getStatus());
		this.image = image;
		this.numDonates = numDonates;
	}

	public VaTiImage getImage() {
		return image;
	}

	public void setImage(VaTiImage image) {
		this.image = image;
	}

	public int getNumDonates() {
		return numDonates;
	}

	public void setNumDonates(int numDonates) {
		this.numDonates = numDonates;
	}
}
