package com.ford.employee_producer_service.model;

import java.time.Instant;
import java.util.Map;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;

//@Data
//@AllArgsConstructor
@Document(collection = "employees_events")
public class EmployeeEvent {

    private String eventId;
    private String status;
    private Map<String, Object> payload;
    private Instant timestamp;
    
    public EmployeeEvent() {}
    
	public EmployeeEvent(String eventId, String status, Map<String, Object> payload, Instant timestamp) {
		super();
		this.eventId = eventId;
		this.status = status;
		this.payload = payload;
		this.timestamp = timestamp;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Map<String, Object> getPayload() {
		return payload;
	}

	public void setPayload(Map<String, Object> payload) {
		this.payload = payload;
	}

	public Instant getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Instant timestamp) {
		this.timestamp = timestamp;
	}
    
}
