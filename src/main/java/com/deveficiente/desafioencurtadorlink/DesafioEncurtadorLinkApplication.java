package com.deveficiente.desafioencurtadorlink;

import java.util.concurrent.Executor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootApplication
@EnableAsync
public class DesafioEncurtadorLinkApplication {

	public static void main(String[] args) {
		SpringApplication.run(DesafioEncurtadorLinkApplication.class, args);
	}
	
	@Value("${maxPoolSize}")
	private int maxPoolSize; 

	@Bean
	public Executor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(2);
		executor.setMaxPoolSize(maxPoolSize);
		//executor.setQueueCapacity(500);
		executor.setThreadNamePrefix("bitly-");
		executor.initialize();
		return executor;
	}

}
