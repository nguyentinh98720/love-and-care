package com.tinhnv.model.others;

import org.springframework.stereotype.Component;

@Component
public class ImgSource {
	private byte[] arr;
	private String type;

	public ImgSource() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ImgSource(byte[] arr, String type) {
		super();
		this.arr = arr;
		this.type = type;
	}

	public byte[] getArr() {
		return arr;
	}

	public void setArr(byte[] arr) {
		this.arr = arr;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
