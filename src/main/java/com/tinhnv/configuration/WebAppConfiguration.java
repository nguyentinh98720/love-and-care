package com.tinhnv.configuration;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;

import com.tinhnv.interceptor.AdminInterceptor;
import com.tinhnv.interceptor.UserInterceptor;
import com.tinhnv.setting.MailConfig;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.tinhnv")
public class WebAppConfiguration extends WebMvcConfigurerAdapter {
	
	@Autowired
	MailConfig mailConfig;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("/resources/");
	}
	
	@Bean
	public TilesConfigurer tilesConfigurer() {
		TilesConfigurer tilesConfigurer = new TilesConfigurer();
		tilesConfigurer.setDefinitions("classpath:tiles.xml");
		tilesConfigurer.setCheckRefresh(true);
		return tilesConfigurer;
	}
	
	@Bean
	public ViewResolver viewResolver() {
		TilesViewResolver tilesViewResolver = new TilesViewResolver();
		return tilesViewResolver;
	}
	
	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver multipartResolver() {
	    CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
	    multipartResolver.setMaxUploadSize(1024*1024*10);
	    return multipartResolver;
	}
	
	//Mail configuration
	@Bean
	public JavaMailSender getJavaMailSender() {
	    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
	    mailSender.setHost(mailConfig.HostSend);
	    mailSender.setPort(mailConfig.TSL_PORT);
	    
	    mailSender.setUsername(mailConfig.email);
	    mailSender.setPassword(mailConfig.password);
	    
	    Properties props = mailSender.getJavaMailProperties();
	    props.put("mail.transport.protocol", "smtp");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.debug", "true");
	    
	    return mailSender;
	}
	
	@Bean
	AdminInterceptor adminInterceptor() {
		return new AdminInterceptor();
	}
	
	@Bean
	UserInterceptor userInterceptor() {
		return new UserInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(adminInterceptor()).addPathPatterns("/admin/**")
		.addPathPatterns("/chuong-trinh/**").addPathPatterns("/tai-khoan/**");
		registry.addInterceptor(userInterceptor()).addPathPatterns("/quyen-gop/**")
		.addPathPatterns("/quan-ly/**");
	}
}
