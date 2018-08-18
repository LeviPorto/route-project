package com.levi.routereceivecoordinateapi.repositories;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
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
import com.levi.route.api.entity.Route;
import com.levi.route.api.entity.Stop;
import com.levi.route.api.enun.RouteStatus;
import com.levi.route.api.enun.StopStatus;
import com.levi.route.api.repository.RouteRepository;
import com.levi.route.api.repository.StopRepository;
import com.levi.route.api.util.DateUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RouteProjectApplication.class)
@ActiveProfiles("test")
public class StopRepositoryTest {

	@Autowired
	private StopRepository stopRepository;
	
	@Autowired
	private RouteRepository routeRepository;
	
	private static final String UPDATE_FINISHED_DATE = "2002-01-04 08:00:00";
	
	@Before
	public void setUp() throws Exception {
		Route route = this.routeRepository.save(returnRouteToTest());
		this.stopRepository.save(returnStopToTest(route));
		Stop firstStopFinised = returnStopToTest(route);
		firstStopFinised.setStopStatus(StopStatus.FINISHED);
		firstStopFinised.setUpdateStatusFinishedDate(DateUtil.sdf.parse("2002-01-04 01:00:00"));
		this.stopRepository.save(firstStopFinised);
		Stop secondStopFinised = returnStopToTest(route);
		secondStopFinised.setStopStatus(StopStatus.FINISHED);
		secondStopFinised.setUpdateStatusFinishedDate(DateUtil.sdf.parse("2002-01-04 08:00:00"));
		this.stopRepository.save(secondStopFinised);
	}

	@After
	public void tearDown() throws Exception {
		this.stopRepository.deleteAll();
	}
	
	@Test
	public void findFinishedStopByRouteTest() {
		List<Stop> stops = stopRepository.findFinishedStopByRoute("2002-01-04 06:00:00");
		assertEquals(1, stops.size());
	}
	
	@Test
	public void findLongerStopByRouteTest() throws ParseException {
		List<Stop> stops = stopRepository.findLongerStopByRoute();
		assertEquals(UPDATE_FINISHED_DATE, DateUtil.sdf.format(stops.get(1).getUpdateStatusFinishedDate()));
	}
	
	private Stop returnStopToTest(Route route) throws ParseException {
		Stop stop = new Stop();
		stop.setRoute(route);
		stop.setDeliveryRadius(30);
		stop.setDescription("Test Description");
		stop.setLat(Long.valueOf(20));
		stop.setLng(Long.valueOf(20));
		stop.setStopStatus(StopStatus.PROGRESS);
		stop.setUpdateStatusProgressDate(DateUtil.sdf.parse("2002-01-04 00:00:00"));
		return stop;
	}
	
	private Route returnRouteToTest() throws ParseException {
		Route route = new Route();
		route.setAssignedVehicle(Long.valueOf(12345));
		route.setRoutePlan("A");
		route.setRouteStatus(RouteStatus.PENDING);
		return route;
	}

}
