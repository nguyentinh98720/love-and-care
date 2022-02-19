package com.tinhnv.model.others;

import org.springframework.stereotype.Component;

@Component
public class AccountStatistics {

	private int total;
	private int adminCount;
	private int userCount;
	private int lockedCount;

	public AccountStatistics() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public void setTotal() {
		this.total = this.adminCount + this.userCount;
	}

	public int getAdminCount() {
		return adminCount;
	}

	public void setAdminCount(int adminCount) {
		this.adminCount = adminCount;
	}

	public int getUserCount() {
		return userCount;
	}

	public void setUserCount(int userCount) {
		this.userCount = userCount;
	}

	public int getLockedCount() {
		return lockedCount;
	}

	public void setLockedCount(int lockedCount) {
		this.lockedCount = lockedCount;
	}

	public AccountStatistics(int adminCount, int userCount, int lockedCount) {
		super();
		this.adminCount = adminCount;
		this.userCount = userCount;
		this.lockedCount = lockedCount;
	}
}
