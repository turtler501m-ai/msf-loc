package com.ktmmobile.mcp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class McpBatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(McpBatchApplication.class, args);
	}

}
