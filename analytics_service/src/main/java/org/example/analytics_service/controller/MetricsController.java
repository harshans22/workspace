package org.example.analytics_service.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MetricsController {
    @GetMapping("/status")
    public String status() {
        return "Analytics service is running";
    }
}
