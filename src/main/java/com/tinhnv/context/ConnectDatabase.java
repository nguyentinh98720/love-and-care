package com.tinhnv.context;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

@Component
public class ConnectDatabase {

	private final String serverName = "ec2-18-235-114-62.compute-1.amazonaws.com";
	private final String databaseName = "d5urrerfbdki23";
	private final String portNumber = "5432";
	private final String instance = "";
	private final String user = "wvhhduidhkhuxq";
	private final String password = "d35664ebef8e439eb0864c4228481134fe5f83835431824f5cde8c166cf01fd9";
	
	private final String driver = "org.postgresql.Driver";	
	
	public JdbcTemplate getConnection() {
		String url = "";
		if(instance == null || instance.trim().isEmpty()) {
			url = "jdbc:postgresql://" + serverName + ":" + portNumber + "/" + databaseName;
		} else {
			url = "jdbc:postgresql://" + serverName + ":" + portNumber + "\\" + instance + "/" + databaseName;
		}

		DriverManagerDataSource driverManage = new DriverManagerDataSource();
		driverManage.setDriverClassName(driver);
		driverManage.setUrl(url);
		driverManage.setUsername(user);
		driverManage.setPassword(password);
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(driverManage);
		
		return jdbcTemplate;
	}
}
