package com.levi.route.api.controller;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.levi.route.api.RouteProjectApplication;
import com.levi.route.api.service.StopService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RouteProjectApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class StopControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private StopService stopService;
	
	private static final String URL_BASE = "/api/stops/";
}
