package com.tinhnv.model.account;

import org.springframework.stereotype.Component;

import com.tinhnv.model.others.VaTiImage;

@Component
public class SessionUser {
	private int id;
	private String firstName;
	private String lastName;
	private String role;
	private VaTiImage avatar;
	private boolean isVerified;
	private boolean isActive;
	
	public SessionUser() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SessionUser(int id, String firstName, String lastName, String role, VaTiImage avatar) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.role = role;
		this.avatar = avatar;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public VaTiImage getAvatar() {
		return avatar;
	}

	public void setAvatar(VaTiImage avatar) {
		this.avatar = avatar;
	}

	public boolean isVerified() {
		return isVerified;
	}

	public void setVerified(boolean isVerified) {
		this.isVerified = isVerified;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isLock) {
		this.isActive = isLock;
	}
}
