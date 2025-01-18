package com.example.spring.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({JpaConfig.class, DataSourceConfig.class, SwaggerConfig.class})
@ComponentScan(basePackages = "com.example.spring")
@Configuration
public class AppConfig {
}
