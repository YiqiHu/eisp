package com.lylj.WebLearning;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
@MapperScan("com.lylj.WebLearning.ORM.Mapper")
@SpringBootApplication
public class WebLearningApplication extends SpringBootServletInitializer {
	
	public static void main(String[] args) {
		SpringApplication.run(WebLearningApplication.class, args);
	}

}
