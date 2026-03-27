package com.ejemplo.usuarios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class UsuariosServiceApplication {

    @Autowired
    private RestTemplate restTemplate;  // Viene de tu AppConfig

    public static void main(String[] args) {
        SpringApplication.run(UsuariosServiceApplication.class, args);
        System.out.println("usuarios-service en http://localhost:8081");
    }

    @GetMapping("/numero")
    @CircuitBreaker(name = "datosServiceCB", fallbackMethod = "obtenerNumeroFallback")
    public String obtenerNumero() {
        System.out.println("=== Intentando llamar a datos-service con @LoadBalanced ===");
        
        String respuesta = restTemplate.getForObject(
                "http://DATOS-SERVICE/numero",
                String.class
        );
        
        return "usuarios-service (load balanced) recibió → " + respuesta;
    }

    // Método Fallback: se ejecuta si DATOS-SERVICE no responde
    public String obtenerNumeroFallback(Exception e) {
        System.out.println("ERROR DETECTADO: " + e.getMessage());
        System.out.println("=== Ejecutando Fallback (Plan B) ===");
        
        return "usuarios-service (fallback) → El servicio de datos está fuera de línea. Retornando valor por defecto: 999";
    }
}