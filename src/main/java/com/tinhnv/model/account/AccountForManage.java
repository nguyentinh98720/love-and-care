package com.tinhnv.model.account;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.tinhnv.model.others.VaTiImage;

@Component
public class AccountForManage {
	private int id;
	private String email;
	private String password;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String address;
	private String role;
	private VaTiImage avatar;
	private boolean status;
	private String saltPass;
	
	public AccountForManage() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AccountForManage(int id, String firstName, String lastName, String phoneNumber,
			String address, String role, VaTiImage avatar, boolean status) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.role = role;
		this.avatar = avatar;
		this.status = status;
	}
	
	public AccountForManage(String email, String password, String firstName, String lastName,
			String phoneNumber, String address, String role, VaTiImage avatar, boolean status,
			String saltPass) {
		super();
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.role = role;
		this.avatar = avatar;
		this.status = status;
		this.saltPass = saltPass;
	}

	public String getSaltPass() {
		return saltPass;
	}

	public void setSaltPass(String saltPass) {
		this.saltPass = saltPass;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public VaTiImage getAvatar() {
		return this.avatar;
	}
	
	public void setAvatar(VaTiImage avatar) {
		this.avatar = avatar;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public AccountForIntroduce toIntroduce(int numEvent, int numDonate, BigDecimal totalMoney) {
		AccountForIntroduce account = new AccountForIntroduce(this.id, this.firstName, this.lastName,
				this.phoneNumber, this.address, this.role, this.avatar, this.status);
		account.setEmail(this.email);
		account.setNumDonateAction(numDonate);
		account.setNumEventJoined(numEvent);
		account.setTotalMoneySpend(totalMoney);
		return account;
	}
	
	@Override
	public String toString() {
		return "email: " + email + " Name: " + firstName + " " + lastName + " Address: " + address;
	}
}
