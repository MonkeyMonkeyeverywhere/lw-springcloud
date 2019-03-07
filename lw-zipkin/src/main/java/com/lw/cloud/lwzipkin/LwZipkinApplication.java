package com.lw.cloud.lwzipkin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import zipkin.server.EnableZipkinServer;

@SpringBootApplication
@EnableZipkinServer
public class LwZipkinApplication {

	public static void main(String[] args) {
		SpringApplication.run(LwZipkinApplication.class, args);
	}

}
