package com.ford.employee_producer_service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ford.employee_producer_service.model.EmployeeEvent;

@Repository
public interface EmployeeEventRepository extends MongoRepository<EmployeeEvent, String> {
    boolean existsById(String eventId);
}
