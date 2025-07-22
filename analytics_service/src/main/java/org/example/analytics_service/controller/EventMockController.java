package org.example.analytics_service.controller;


import org.example.analytics_service.model.ActivityEvent;
import org.example.analytics_service.service.EventConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mock-event")
public class EventMockController {

    private final EventConsumer eventConsumer;

    @Autowired
    public EventMockController(EventConsumer eventConsumer) {
        this.eventConsumer = eventConsumer;
    }

    @PostMapping
    public String simulateEvent(@RequestBody ActivityEvent event) {
        eventConsumer.processEvent(event);
        return "Mock event processed successfully";
    }
}

