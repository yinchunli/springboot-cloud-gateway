package com.yingchun.li.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringCloudConfig {
    @Autowired
    private CustomizedFilter filter;
    
    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {

        return builder.routes().route(r -> r
                .alwaysTrue()
                .filters(f -> f.filter(filter))
                .uri("no://op"))
                .build();
    }
}

