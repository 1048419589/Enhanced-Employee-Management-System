package com.empManagement.empManagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.thymeleaf.dialect.springdata.SpringDataDialect;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//网站启动就直接运行这个文件 之后访问网址http://localhost:8083/
@SpringBootApplication
public class EmpManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmpManagementApplication.class, args);

	}

//	@Bean
//	public SpringDataDialect springDataDialect() {
//		return new SpringDataDialect();
//	}

}
