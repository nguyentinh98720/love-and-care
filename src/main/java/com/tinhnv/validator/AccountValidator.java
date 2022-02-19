package com.tinhnv.validator;

import org.springframework.stereotype.Component;

@Component
public class AccountValidator {
	private String message;

	private final String emailrejex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\."
			+ "[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-"
			+ "\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-"
			+ "\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-"
			+ "z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-"
			+ "9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-"
			+ "z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-"
			+ "\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
	
	private String email;
	private String password;
	
	public AccountValidator() {
		super();
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

	public AccountValidator(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}
	
	public boolean checkAccount(String encode, String saltPass) {
		if(!checkEmail(this.email)) return false;
		HashPassword hashPass = new HashPassword(this.password);
		return hashPass.verify(encode, saltPass);
	}
	
	public boolean checkEmail(String email) {
		if(email.matches(emailrejex)) {
			return true;
		}
		this.message = "Email không hợp lệ";
		return false;
	}
	
	public String getMessage() {
		String mes = this.message;
		this.message = "";
		return mes;
	}
}