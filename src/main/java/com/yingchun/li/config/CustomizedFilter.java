package com.yingchun.li.config;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.RouteToRequestUrlFilter;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public	class CustomizedFilter implements GatewayFilter, Ordered {

	    @Override
	    public int getOrder() {
	        return RouteToRequestUrlFilter.ROUTE_TO_URL_FILTER_ORDER + 1;
	    }

	    @Override
	    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
	        String newUrl = null;

	        if (exchange.getRequest().getHeaders().getHost().toString().equals("localhost:8080")) {
	        	if(exchange.getRequest().getPath().toString().contains("history") && 
	        			Integer.valueOf(exchange.getRequest().getPath().subPath(11).toString()).intValue()>3)
	        		newUrl = "http://localhost:8081"+exchange.getRequest().getPath().toString();
	        	else
	        		newUrl = "http://localhost:8082"+exchange.getRequest().getPath().toString();
	        } else {
	            newUrl = "no://op";
	        }

	        try {
				exchange.getAttributes().put(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR, new URI(newUrl));
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        return chain.filter(exchange);
	    }
	}
