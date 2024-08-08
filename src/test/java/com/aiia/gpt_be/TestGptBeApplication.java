package com.aiia.gpt_be;

import org.springframework.boot.SpringApplication;

public class TestGptBeApplication {

	public static void main(String[] args) {
		SpringApplication.from(GptBeApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
