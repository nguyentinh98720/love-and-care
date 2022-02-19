package com.tinhnv.model.others;

import org.springframework.stereotype.Component;

@Component
public class ImageResponse {

	private boolean uploaded;
	private String url;
	private Exception err;

	public ImageResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ImageResponse(boolean uploaded, String url, Exception err) {
		super();
		this.uploaded = uploaded;
		this.url = url;
		this.err = err;
	}

	public boolean isUploaded() {
		return uploaded;
	}

	public void setUploaded(boolean uploaded) {
		this.uploaded = uploaded;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Exception getErr() {
		return err;
	}

	public void setErr(Exception err) {
		this.err = err;
	}

}
