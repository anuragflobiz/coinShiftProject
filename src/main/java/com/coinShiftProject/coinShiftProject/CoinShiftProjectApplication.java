package com.coinShiftProject.coinShiftProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CoinShiftProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoinShiftProjectApplication.class, args);
	}

}
