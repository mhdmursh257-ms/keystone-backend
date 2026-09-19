package com.key_stone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class KeyStoneApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(KeyStoneApplication.class, args);
		context.registerShutdownHook();
	}

}
