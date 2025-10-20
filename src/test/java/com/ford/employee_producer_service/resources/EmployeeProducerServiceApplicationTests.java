package com.ford.employee_producer_service.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmployeeProducerServiceApplicationTests {
	
	@Test
    void contextLoads() {
		String test="test";
        assertEquals("test", test);
    }

}
