package com.springpractice.apiGateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

    @Bean
    @LoadBalanced
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("product_route", p -> p
                        .path("/api/product")
                        .uri("lb://product-service"))
                .route("order_route", p -> p
                        .path("/api/order")
                        .uri("lb://order-service"))
                .route("discovery_route", p -> p
                        .path("/eureka/web")
                        .filters(f -> f.setPath("/"))
                        .uri("http://localhost:8761"))
                .route("discovery_route_static", p -> p
                        .path("/eureka/**")
                        .uri("http://localhost:8761"))
                .build();
    }
}
