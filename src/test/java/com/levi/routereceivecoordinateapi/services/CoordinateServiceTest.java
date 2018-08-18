package com.levi.routereceivecoordinateapi.services;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;

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
		BDDMockito.given(this.coordinateRepository.findTop2ByVehicleId(Mockito.anyLong(), Mockito.any(PageRequest.class))).willReturn(
				new PageImpl<Coordinate>(new ArrayList<Coordinate>()));
	}

	@SuppressWarnings("deprecation")
	@Test
	public void findTop2ByVehicleIdTest() {
		Page<Coordinate> coordinates = this.coordinateService.receiveVehicleCoordinates(1L,  new PageRequest(0, 2));
		assertNotNull(coordinates);
	}
	
}
