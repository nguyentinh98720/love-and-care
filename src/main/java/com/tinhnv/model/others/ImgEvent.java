package com.tinhnv.model.others;

import org.springframework.stereotype.Component;

@Component
public class ImgEvent extends VaTiImage{
	private int id;
	private int eventId;
	
	public ImgEvent () {
		super();
	}
	
	public ImgEvent (byte[] arrByte, String typeImg, int id) {
		super();
		this.id = id;
		setArr(arrByte);;
		setType(typeImg);;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public void setType (String type) {
		super.setType(type);
	}
	
	public void setArray (byte[] arr) {
		super.setArr(arr);;
	}
	
	public String getType () {
		return super.getType();
	}
	
	public byte[] getArray () {
		return super.getArr();
	}
	
	@Override
	public String toString() {
		return super.getArr().length + " : " + super.getType();
	}
}
