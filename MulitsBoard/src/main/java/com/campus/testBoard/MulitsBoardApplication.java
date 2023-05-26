package com.campus.testBoard;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class MulitsBoardApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(MulitsBoardApplication.class)
		.properties("spring.config.location=classpath:BoardTestApplication.yml")
		.run(args);
	}
}
