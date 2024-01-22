package com.nttdatabc.mscuentabancaria;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


@SpringBootApplication
@EnableEurekaClient
public class MscuentabancariaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MscuentabancariaApplication.class, args);
	}

}
