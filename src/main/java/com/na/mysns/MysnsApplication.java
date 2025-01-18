package com.na.mysns;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class MysnsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MysnsApplication.class, args);
	}

}
