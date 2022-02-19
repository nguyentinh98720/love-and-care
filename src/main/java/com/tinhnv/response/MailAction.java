package com.tinhnv.response;

import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tinhnv.setting.MailConfig;

@Component
public class MailAction {

	@Autowired
	MailConfig mailConfig;
	
	public MailAction() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Session Login(String username, String pass) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", mailConfig.HostSend);
        props.put("mail.smtp.socketFactory.port", mailConfig.SSL_PORT);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.port", mailConfig.SSL_PORT);

        PasswordAuthentication Auth = new PasswordAuthentication(username, pass);
        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return Auth;
            }
        });
        return session;
    }
	
	public boolean sendMail(String to, String content, String subject) throws MessagingException {
		Session session = Login(mailConfig.email, mailConfig.password);
		InternetAddress emailTo = new InternetAddress(to);
        MimeMessage mimeMss = new MimeMessage(session);
        mimeMss.setSubject(subject);
        mimeMss.setRecipient(RecipientType.TO, emailTo);
        BodyPart body = new MimeBodyPart();
        body.setText(content);
        Multipart part = new MimeMultipart();
        part.addBodyPart(body);
        mimeMss.setContent(part);
        Transport.send(mimeMss);
		return true;
	}
}
