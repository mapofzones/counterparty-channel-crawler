package com.mapofzones.counterpartychannelcrawler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CrawlerApp {

	public static void main(String[] args) {
		SpringApplication.run(CrawlerApp.class, args);
	}

}