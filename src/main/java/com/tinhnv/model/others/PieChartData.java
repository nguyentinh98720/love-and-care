package com.tinhnv.model.others;

public class PieChartData {

	private int running;
	private int stopping;
	private int ending;
	
	public PieChartData() {
		super();
	}
	
	public PieChartData(int running, int stopping, int ending) {
		this.running = running;
		this.stopping = stopping;
		this.ending = ending;
	}

	public int getRunning() {
		return running;
	}

	public void setRunning(int running) {
		this.running = running;
	}

	public int getStopping() {
		return stopping;
	}

	public void setStopping(int stopping) {
		this.stopping = stopping;
	}

	public int getEnding() {
		return ending;
	}

	public void setEnding(int ending) {
		this.ending = ending;
	}
	
}
