package com.levi.routereceivecoordinateapi.repositories;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.levi.route.api.RouteProjectApplication;
import com.levi.route.api.entity.Coordinate;
import com.levi.route.api.repository.CoordinateRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RouteProjectApplication.class)
@ActiveProfiles("test")
public class CoordinateRepositoryTest {

	@Autowired
	private CoordinateRepository coordinateRepository;
	
	@Before
	public void setUp() throws Exception {
		this.coordinateRepository.save(returnCoordinateToTest());
		this.coordinateRepository.save(returnCoordinateToTest());
	}

	@After
	public void tearDown() throws Exception {
		this.coordinateRepository.deleteAll();
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void findTop2ByVehicleId() {
		PageRequest page = new PageRequest(0, 2);
		Page<Coordinate> routes = coordinateRepository.findTop2ByVehicleId(Long.valueOf(12345), page);
		assertEquals(2, routes.getTotalElements());
	}
	
	private Coordinate returnCoordinateToTest() throws ParseException {
		Coordinate coordinate = new Coordinate();
		coordinate.setInstant(new Date());
		coordinate.setLat(20.0);
		coordinate.setLng(20.0);
		coordinate.setVehicleId(Long.valueOf(12345));
		return coordinate;
	}
}
