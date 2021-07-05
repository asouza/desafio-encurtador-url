package com.deveficiente.desafioencurtadorlink;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class DesafioEncurtadorLinkApplication {

	public static void main(String[] args) {
		SpringApplication.run(DesafioEncurtadorLinkApplication.class, args);
	}

}
