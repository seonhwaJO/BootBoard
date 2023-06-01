package com.multi.board.multiboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class MultiBoardApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(MultiBoardApplication.class)
                .properties("spring.config.location=classpath:MultiBoardApplication.yml")
                .run(args);
    }
}
