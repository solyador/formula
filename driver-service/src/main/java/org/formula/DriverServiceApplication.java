package org.formula;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableWebMvc
public class DriverServiceApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(DriverServiceApplication.class, args);
	}

	@Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }
}
