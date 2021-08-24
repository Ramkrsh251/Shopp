package com.app;

import static org.junit.jupiter.api.Assertions.*;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.app.exception.BusinessException;
import com.app.model.Customer;
import com.app.search.service.impl.SearchServiceImpl;
import com.search.service.SearchService;

class TestCase1 extends Main {
	private static Logger log = Logger.getLogger(TestCase1.class);
	SearchService searchService;
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		 searchService=new SearchServiceImpl();
		
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testUserRegistration(){
		Customer customer=new Customer();
		customer.setCust_id(14);
		customer.setCust_pwd("Password");
		customer.setCust_email("testcase@gmail.com");
		customer.setCust_name("TestName");
		try {
		assertEquals(1,searchService.RegisterService(customer));
		}
		catch(BusinessException e) {
			log.warn(e.getMessage());
		}
	}

}
