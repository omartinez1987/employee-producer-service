package com.ford.employee_producer_service.resources;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ford.employee_producer_service.controller.EmployeeController;
import com.ford.employee_producer_service.model.Employee;
import com.ford.employee_producer_service.model.EmployeeEvent;
import com.ford.employee_producer_service.service.EmployeeService;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

	@Autowired
    private MockMvc mockMvc;
	
	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testPublishMessage() throws Exception {
    	
        Employee employee = new Employee("Benjamin White", 28, "Right Lateral");

        mockMvc.perform(post("/employee/create")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isCreated())
                .andExpect(content().json("{\"ERROR\":false}"));

        verify(employeeService, times(1)).sendMessage(employee);
    }

}
