package com.ford.employee_producer_service.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ford.employee_producer_service.model.Employee;
import com.ford.employee_producer_service.service.EmployeeService;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService kafkaProducerService;

    public EmployeeController(EmployeeService kafkaProducerService) {
        this.kafkaProducerService = kafkaProducerService;
    }

    @PostMapping("/create")
    public ResponseEntity<Object> publishMessage(@RequestBody Employee employee) {
    	Map<String, Object> response = new HashMap();
    	try {
    		kafkaProducerService.sendMessage(employee);
    		response.put("ERROR", false);
            return new ResponseEntity<Object>(response, HttpStatus.CREATED);
    	}catch(Exception ex) {
    		response.put("ERROR", true);
            return new ResponseEntity<Object>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    	}
        
    }

}
