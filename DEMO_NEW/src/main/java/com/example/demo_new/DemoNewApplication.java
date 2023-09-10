package com.example.demo_new;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication_ {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication_.class, args);
	}

	@SpringBootApplication
	public static class DemoApplication {

		public static void main(String[] args) {
			SpringApplication.run(DemoApplication.class, args);
		}

	}
}
