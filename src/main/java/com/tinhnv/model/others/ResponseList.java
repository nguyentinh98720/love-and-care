package com.tinhnv.model.others;

import java.util.List;

import org.springframework.stereotype.Component;

import com.tinhnv.model.event.EventHomePage;

@Component
public class ResponseList {
	private int total;
	private List<EventHomePage> list;

	public ResponseList() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ResponseList(int total, List<EventHomePage> list) {
		super();
		this.total = total;
		this.list = list;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<EventHomePage> getList() {
		return list;
	}

	public void setList(List<EventHomePage> list) {
		this.list = list;
	}

}
