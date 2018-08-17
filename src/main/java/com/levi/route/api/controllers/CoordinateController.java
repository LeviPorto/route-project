package com.levi.route.api.controllers;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.levi.route.api.dtos.CoordinateDto;
import com.levi.route.api.entities.Coordinate;
import com.levi.route.api.entities.Route;
import com.levi.route.api.entities.Stop;
import com.levi.route.api.enums.RouteStatus;
import com.levi.route.api.enums.StopStatus;
import com.levi.route.api.response.Response;
import com.levi.route.api.services.CoordinateService;
import com.levi.route.api.services.RouteService;
import com.levi.route.api.services.StopService;
import com.levi.route.api.utils.DateUtil;
import com.levi.route.api.utils.HaversinCalculatorUtil;

@RestController
@RequestMapping("/api/routes")
@CrossOrigin(origins = "*")
public class CoordinateController {

private static final Logger log = LoggerFactory.getLogger(RouteController.class);
	
	@Autowired
	private CoordinateService coordinateService;
	
	@Autowired
	private RouteService routeService;
	
	@Autowired
	private StopService stopService;
	
	public CoordinateController() {
		
	}
	
	@SuppressWarnings("deprecation")
	@GetMapping(value = "/receiveCoordinate/{vehicleId}")
	public ResponseEntity<Response<Page<CoordinateDto>>> receiveCoordinateAndUpdateStatus(
			@PathVariable("vehicleId") Long vehicleId,@RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "DESC") String dir) throws ParseException {
		
		log.info("Finding coordinate for vehicleId: {}", vehicleId);
		Response<Page<CoordinateDto>> response = new Response<Page<CoordinateDto>>();
		
		PageRequest pageRequest = new PageRequest(pag, 2, Direction.valueOf(dir), ord);
		
		Page<Coordinate> coordinates = this.coordinateService.receiveVehicleCoordinates(vehicleId, pageRequest);
		Optional<List<Route>> routes = this.routeService.findPendentByVehicleId(vehicleId);
		
		for(int i = 0;i<routes.get().size();++i) {
			Coordinate lastCoordinate = coordinates.getContent().get(0);
			Coordinate penultimateCoordinate = coordinates.getContent().get(1);
			Route route = routes.get().get(i);
			
			changeRouteStatusToProgress(lastCoordinate, route);
			
			int countOfFinishedStopByRoute = 0;
			for(int y = 0;y<route.getPlannedStops().size();++y) {
				Stop stop = route.getPlannedStops().get(y);
				
				changeStopStatusToProgress(lastCoordinate, penultimateCoordinate, stop);
				changeStopStatusToFinished(lastCoordinate, stop);
				
				if(stop.getStopStatus().toString().equals("FINISHED")) {
					countOfFinishedStopByRoute++;
				}
				
			}
		
			changeRouteStatusToFinished(route, countOfFinishedStopByRoute);
		}
		
		Page<CoordinateDto> coordinateDto = coordinates.map(coordinate -> this.convertCoordinateToDto(coordinate));
		response.setData(coordinateDto);
		
		return ResponseEntity.ok(response);
	}

	private void changeRouteStatusToFinished(Route route, int countOfFinishedStopByRoute) {
		if(countOfFinishedStopByRoute == route.getPlannedStops().size()) {
			route.setRouteStatus(RouteStatus.FINISHED);
			route.setUpdateStatusFinishedDate(new Date());
			routeService.persist(route);
		}
	}

	private void changeStopStatusToFinished(Coordinate lastCoordinate, Stop stop) {
		if(stop.getStopStatus().toString().equals("PROGRESS") && 
				(HaversinCalculatorUtil.distanceBetweenTwoPoints(lastCoordinate.getLat()
			, lastCoordinate.getLng(),stop.getLat(), stop.getLng()) > stop.getDeliveryRadius())) {
			stop.setStopStatus(StopStatus.FINISHED);
			stop.setUpdateStatusFinishedDate(new Date());
			stopService.persist(stop);
		}
	}

	private void changeStopStatusToProgress(Coordinate lastCoordinate, Coordinate penultimateCoordinate, Stop stop)
			throws ParseException {
		if((HaversinCalculatorUtil.distanceBetweenTwoPoints(lastCoordinate.getLat(), 
				lastCoordinate.getLng(),stop.getLat(),stop.getLng()) <= 
				stop.getDeliveryRadius()) && (HaversinCalculatorUtil.distanceBetweenTwoPoints(penultimateCoordinate.getLat()
				, penultimateCoordinate.getLng() ,stop.getLat(),stop.getLng()) <= 
				stop.getDeliveryRadius())){
			
			if(DateUtil.getDateDiff(penultimateCoordinate.getInstant(),
					lastCoordinate.getInstant(), TimeUnit.MINUTES) <= 5){
				
				stop.setStopStatus(StopStatus.PROGRESS);
				stop.setUpdateStatusProgressDate(new Date());
				stopService.persist(stop);
			}
			
		}
	}

	private void changeRouteStatusToProgress(Coordinate lastCoordinate, Route route) {
		if(lastCoordinate.getVehicleId().equals(route.getAssignedVehicle())) {
			route.setRouteStatus(RouteStatus.PROGRESS);
			route.setUpdateStatusProgressDate(new Date());
			routeService.persist(route);
		}
	}
	
	private CoordinateDto convertCoordinateToDto(Coordinate coordinate) {
		CoordinateDto coordinateDto = new CoordinateDto();
		
		coordinateDto.setId(coordinate.getId());
		coordinateDto.setInstant(String.valueOf(coordinate.getInstant()));
		coordinateDto.setLat(String.valueOf(coordinate.getLat()));
		coordinateDto.setLng(String.valueOf(coordinate.getLng()));
		coordinateDto.setVehicleId(String.valueOf(coordinate.getVehicleId()));

		return coordinateDto;
	}
}
