/*
 * Tương ứng với file web.xml khi cấu hình bằng file xml
 */

package com.tinhnv.configuration;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

@Configuration
public class WebInitalizer implements WebApplicationInitializer {
	
	private String TMP_FOLDER = "/tmp"; 
    private int MAX_UPLOAD_SIZE = 5 * 1024 * 1024;
	
	public void onStartup(ServletContext container) throws ServletException {
		AnnotationConfigWebApplicationContext annotationConfig = new AnnotationConfigWebApplicationContext();
		annotationConfig.register(WebAppConfiguration.class);
		annotationConfig.setServletContext(container);
		
		DispatcherServlet dispatcherServlet = new DispatcherServlet(annotationConfig);
		dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
		
		ServletRegistration.Dynamic servlet = container.addServlet("dispatcher", dispatcherServlet);
		servlet.setLoadOnStartup(1);
		servlet.addMapping("/");
		
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter("UTF-8", true);
		container.addFilter("encodeFilter", characterEncodingFilter).addMappingForUrlPatterns(null, false, "/*");
		
		MultipartConfigElement multipartConfigElement = new MultipartConfigElement(TMP_FOLDER, 
		          MAX_UPLOAD_SIZE, MAX_UPLOAD_SIZE * 2, MAX_UPLOAD_SIZE / 2);
		        
        servlet.setMultipartConfig(multipartConfigElement);
	}
}
