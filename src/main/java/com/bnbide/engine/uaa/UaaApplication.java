package com.bnbide.engine.uaa;

import com.bnbide.engine.uaa.config.uaa.UaaConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({ UaaConfig.class })
public class UaaApplication {

	public static void main(String[] args) {
		SpringApplication.run(UaaApplication.class, args);
	}

}
