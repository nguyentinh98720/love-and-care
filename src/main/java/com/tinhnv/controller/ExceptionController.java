package com.tinhnv.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class ExceptionController {
	
	private static Logger logger = Logger.getLogger(ExceptionController.class);

	@ExceptionHandler(value={NoHandlerFoundException.class})
	public String pageNotFound(Exception exception) {
		logger.error(exception);
		return "404";
	}
	
	@ExceptionHandler(value={SQLException.class})
	public String sqlException(SQLException exception, HttpServletRequest request) {
		logger.error(exception);
		request.setAttribute("message", exception.getMessage());
		return "error";
	}
	
	@ExceptionHandler(value={HttpRequestMethodNotSupportedException.class})
	public String pageError(Exception exception, HttpServletRequest request) {
		logger.error(exception);
		request.setAttribute("message", "Trang hiện tại đang chấp nhận một phương thức yêu cầu khác!");
		return "error";
	}
}
