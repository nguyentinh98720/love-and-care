package com.tinhnv.model.others;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

@Component
public class VaTiImage extends ImgSource {
	private String encode;
	
	public VaTiImage() {
		super();
	}
	
	public VaTiImage(byte[] arr, String type) {
		super(arr, type);
		this.encode = Base64.encodeBase64String(arr);
	}

	@Override
	public void setArr(byte[] arr) {
		super.setArr(arr);
		this.encode = Base64.encodeBase64String(arr);
	}

	public String getEncode() {
		return encode;
	}
}
