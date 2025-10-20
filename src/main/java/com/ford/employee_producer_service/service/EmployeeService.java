package com.ford.employee_producer_service.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.ford.employee_producer_service.model.Employee;
import com.ford.employee_producer_service.model.EmployeeEvent;
import com.ford.employee_producer_service.repository.EmployeeEventRepository;
import org.springframework.dao.DuplicateKeyException;

@Service
public class EmployeeService {
	
	private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    private final KafkaTemplate<String, EmployeeEvent> kafkaTemplate;

    private final EmployeeEventRepository repository;

    @Value("${kafka.topic.employee}")
    private String employeeTopic;

    public EmployeeService(KafkaTemplate<String, EmployeeEvent> kafkaTemplate, EmployeeEventRepository repository) {
        this.kafkaTemplate = kafkaTemplate;
        this.repository = repository;
    }

    public void sendMessage(Employee employee) {
    	logger.info("Received employee: {}", employee);
        EmployeeEvent eventMessage = new EmployeeEvent();
        eventMessage.setEventId(UUID.randomUUID().toString());
        eventMessage.setStatus("CREATED");
        eventMessage.setPayload(Map.of("employee", employee));

        // Step 1: Save event first (idempotency)
        saveEvent(eventMessage);

        // Send asynchronously to Kafka
        kafkaTemplate.send(employeeTopic, eventMessage.getEventId(), eventMessage)
                .whenComplete((result, exception) -> {
                    if (exception == null) {
                        System.out.println("✅ Sent message successfully: " + eventMessage);
                    } else {
                        System.err.println("❌ Failed to send message: " + eventMessage);
                        exception.printStackTrace();
                        retrySend(eventMessage, 5);
                    }
                });
        logger.debug("Message successfully sent to Kafka for {}", eventMessage);
    }

    public void saveEvent(EmployeeEvent event) {
        try {
            repository.insert(event); // fails if _id exists
        } catch (DuplicateKeyException e) {
            System.out.println("Event " + event.getEventId() + " already saved, skipping.");
        } catch (Exception e) {
            // Retry logic or throw a custom exception
            throw new RuntimeException("Failed to save event: " + event.getEventId(), e);
        }
    }

    private void retrySend(EmployeeEvent event, int retries) {
        for (int i = 1; i <= retries; i++) {
            try {
                kafkaTemplate.send(employeeTopic, event).get(5, TimeUnit.SECONDS);
                System.out.println("✅ Retried successfully on attempt " + i);
                return;
            } catch (Exception ex) {
                System.err.println("Retry " + i + " failed: " + ex.getMessage());
            }
        }
        System.err.println("🚨 Giving up after " + retries + " retries for event " + event.getEventId());
    }

    // private void handleSendFailure(EmployeeEvent event, Throwable ex) {
    // // Log or mark the event as failed
    // System.err.println("Kafka send failed for event " + event.getEventId() + ": "
    // + ex.getMessage());
    // // You could update MongoDB event status to "FAILED" or retry
    // }
}
