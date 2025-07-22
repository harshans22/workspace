package org.example.analytics_service.service;



import io.micrometer.core.instrument.MeterRegistry;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.example.analytics_service.model.ActivityEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class EventConsumer {

    private final MeterRegistry registry;
    private final ConcurrentHashMap<String, AtomicLong> eventTypeCounters = new ConcurrentHashMap<>();

    @Autowired
    public EventConsumer(MeterRegistry registry) {
        this.registry = registry;
    }

    @KafkaListener(topics = "activity-events", groupId = "analytics-group")
    public void listen(ConsumerRecord<String, ActivityEvent> record) {
        if (record != null && record.value() != null) {
            processEvent(record.value());
        }
    }

    // Make this public so it can be used by the mock controller
    public void processEvent(ActivityEvent event) {
        String type = event.getType();
        eventTypeCounters.computeIfAbsent(type, k -> {
            registry.counter("platform.activity.events", "type", k);
            return new AtomicLong();
        }).incrementAndGet();
        registry.counter("platform.activity.events", "type", type).increment();
    }
}


