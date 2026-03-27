package com.ejemplo.datos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.env.Environment;  
import org.springframework.beans.factory.annotation.Autowired; 
import java.util.Random;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class DatosServiceApplication {

    private Random random = new Random();
    
    @Autowired  // ← INYECCIÓN
    private Environment environment;  // ← DECLARAR

    public static void main(String[] args) {
        SpringApplication.run(DatosServiceApplication.class, args);
        System.out.println("datos-service en http://localhost:8082");
    }

    @GetMapping("/numero")
    public String numero() {
        String puerto = environment.getProperty("server.port");  // ← USAR
        return "Instancia en puerto " + puerto + " → " + random.nextInt(1000);
    }
}