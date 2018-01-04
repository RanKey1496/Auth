package com.jaime.auth.config;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableConfigurationProperties
@EnableAutoConfiguration
@EnableTransactionManagement
public class DataSourceConfig {

	@Primary
	@Bean(name = "SAP2")
	@ConfigurationProperties(prefix = "spring.SAP2.datasource")
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}
	
	@Bean(name = "SAP2General")
	@ConfigurationProperties(prefix = "spring.SAP2General.datasource")
	public DataSource dataSourceSecondary() {
		return DataSourceBuilder.create().build();
	}
	
}
