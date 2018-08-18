package com.levi.route.api.repository;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
	
	@Test
	public void findTop2ByVehicleId() {
		List<Coordinate> routes = coordinateRepository.findLastTop2ByVehicle(Long.valueOf(12345));
		assertEquals(2, routes.size());
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
