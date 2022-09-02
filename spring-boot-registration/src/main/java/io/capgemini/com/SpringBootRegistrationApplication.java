package io.capgemini.com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EnableEurekaClient
public class SpringBootRegistrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootRegistrationApplication.class, args);
	}

	@Bean
	PasswordEncoder encoder(){
		return new BCryptPasswordEncoder();
	}
}
