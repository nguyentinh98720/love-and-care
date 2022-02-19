package com.tinhnv.response;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class ConnectEmail {
	
	@Autowired
	MailAction mailAction;

	public ConnectEmail() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public boolean sendPassword(String email, String newPass) {
		String subject = "LoveAndCare - Mật khẩu đăng nhập";
		String message = "Xin chào " + email + ". Mật khẩu đăng nhập của bạn là: " + newPass;
		try {
			mailAction.sendMail(email, message, subject);
		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
