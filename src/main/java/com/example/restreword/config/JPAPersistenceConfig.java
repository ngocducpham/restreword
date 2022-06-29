package com.example.restreword.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.example.restreword.repo")
@PropertySource("classpath:datasource.properties")
@EntityScan(basePackages={ "com.example.restreword.entity" })
public class JPAPersistenceConfig {

}