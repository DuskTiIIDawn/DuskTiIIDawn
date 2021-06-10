package com.example.StockMarketCharting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;



@SpringBootApplication
@EnableAutoConfiguration
@EntityScan(basePackages ={ "com.example.StockMarketCharting.entities"})
public class StockMarketChartingApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockMarketChartingApplication.class, args);
	}

}
