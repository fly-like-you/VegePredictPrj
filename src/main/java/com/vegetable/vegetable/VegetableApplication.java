package com.vegetable.vegetable;

import com.vegetable.vegetable.service.MainService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
@EnableAsync
public class VegetableApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(VegetableApplication.class, args);
	}

}
