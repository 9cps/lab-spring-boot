package com.cps.pwm.controller;

import com.cps.pwm.service.HealthCheckService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/health")
public class HealthCheckController {

    private final HealthCheckService healthCheckService;

    public HealthCheckController(HealthCheckService healthCheckService) {
        this.healthCheckService = healthCheckService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> checkHealth() {
        Map<String, Object> status = new HashMap<>();
        status.put("service", "UP");
        status.put("database", healthCheckService.isDatabaseUp() ? "UP" : "DOWN");

        return ResponseEntity.ok(status);
    }

    @GetMapping("/debug-hostname")
    public String debugHostname() throws UnknownHostException {
        String hostname = InetAddress.getLocalHost().getHostName();
        return "Hostname: " + hostname;
    }
}