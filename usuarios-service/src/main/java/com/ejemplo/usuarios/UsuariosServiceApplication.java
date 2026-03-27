package com.ejemplo.usuarios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class UsuariosServiceApplication {

    @Autowired
    private RestTemplate restTemplate;  // Esto ahora viene de AppConfig

    public static void main(String[] args) {
        SpringApplication.run(UsuariosServiceApplication.class, args);
        System.out.println("usuarios-service en http://localhost:8081");
    }

    @GetMapping("/numero")
    public String obtenerNumero() {
        System.out.println("=== Intentando llamar a datos-service con @LoadBalanced ===");
        
        try {
            String respuesta = restTemplate.getForObject(
                    "http://DATOS-SERVICE/numero",
                    String.class
            );
            
            return "usuarios-service (load balanced) recibió → " + respuesta;
            
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getClass().getName());
            System.out.println("MENSAJE: " + e.getMessage());
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }
}