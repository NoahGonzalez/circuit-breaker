package com.ejemplo.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayServiceApplication.class, args);
        System.out.println("🚀 Gateway en http://localhost:8080");
        System.out.println("📡 Endpoints disponibles:");
        System.out.println("   - http://localhost:8080/api/100  → usuarios-service");
        System.out.println("   - http://localhost:8080/api/200  → datos-service");
        System.out.println("   - http://localhost:8080/api/300  → pagos-service");
        System.out.println("   - http://localhost:8080/api/400  → notificaciones-service");
    }
    
    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
            // CÓDIGO 100 → Usuarios Service
            .route("codigo-100", r -> r
                .path("/api/100")
                .filters(f -> f.setPath("/numero"))
                .uri("lb://USUARIOS-SERVICE"))
                
            .route("codigo-100-param", r -> r
                .path("/api/100/{id}")
                .filters(f -> f.setPath("/usuarios/{id}"))
                .uri("lb://USUARIOS-SERVICE"))
            
            // CÓDIGO 200 → Datos Service
            .route("codigo-200", r -> r
                .path("/api/200")
                .filters(f -> f.setPath("/numero"))
                .uri("lb://DATOS-SERVICE"))
                
            .route("codigo-200-param", r -> r
                .path("/api/200/{id}")
                .filters(f -> f.setPath("/datos/{id}"))
                .uri("lb://DATOS-SERVICE"))
            
            // CÓDIGO 300 → Pagos Service
            .route("codigo-300", r -> r
                .path("/api/300")
                .filters(f -> f.setPath("/pagos"))
                .uri("lb://PAGOS-SERVICE"))
                
            .route("codigo-300-crear", r -> r
                .path("/api/300")
                .and().method("POST")
                .filters(f -> f.setPath("/pagos/crear"))
                .uri("lb://PAGOS-SERVICE"))
            
            // CÓDIGO 400 → Notificaciones Service
            .route("codigo-400", r -> r
                .path("/api/400")
                .filters(f -> f.setPath("/notificaciones"))
                .uri("lb://NOTIFICACIONES-SERVICE"))
            
            .build();
    }
}