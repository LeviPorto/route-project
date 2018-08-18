package com.levi.route.api.service;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.levi.route.api.RouteProjectApplication;
import com.levi.route.api.entity.Coordinate;
import com.levi.route.api.entity.Route;
import com.levi.route.api.repository.CoordinateRepository;
import com.levi.route.api.service.CoordinateService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RouteProjectApplication.class)
@ActiveProfiles("test")
public class CoordinateServiceTest {

	@MockBean
	private CoordinateRepository coordinateRepository;

	@Autowired
	private CoordinateService coordinateService;
	
	@Before
	public void setUp() throws Exception {
		BDDMockito.given(this.coordinateRepository.save(Mockito.any(Coordinate.class))).willReturn(new Coordinate());
		BDDMockito.given(this.coordinateRepository.findLastTop2ByVehicle(Mockito.anyLong())).willReturn(new ArrayList<Coordinate>());
	}

	@Test
	public void findTop2ByVehicleIdTest() {
		List<Coordinate> coordinates = this.coordinateService.findLastTop2ByVehicle(1L);
		assertNotNull(coordinates);
	}
	
}
