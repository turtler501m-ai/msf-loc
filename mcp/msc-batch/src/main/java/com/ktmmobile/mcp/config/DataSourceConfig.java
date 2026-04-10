package com.ktmmobile.mcp.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class DataSourceConfig {

	@Bean
	@ConfigurationProperties("spring.datasource.hikari.bootoradb")
	public DataSource bootoradbDataSource() {
		return DataSourceBuilder.create().type(HikariDataSource.class).build();
	}

	@Bean
	@ConfigurationProperties("spring.datasource.hikari.booteventdb")
	public DataSource booteventdbDataSource() {
		return DataSourceBuilder.create().type(HikariDataSource.class).build();
	}

	@Bean
	public PlatformTransactionManager bootOraTransactionManager(@Qualifier(value="bootoradbDataSource") DataSource dataSource){
		return new DataSourceTransactionManager(dataSource);
	}

}
