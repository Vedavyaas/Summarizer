package com.Summarizer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Async;

@SpringBootApplication
@Async
public class SummarizerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SummarizerApplication.class, args);
	}

}