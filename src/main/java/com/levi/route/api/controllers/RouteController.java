package com.levi.route.api.controllers;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.levi.route.api.dtos.RouteDto;
import com.levi.route.api.entities.Route;
import com.levi.route.api.enums.RouteStatus;
import com.levi.route.api.enums.StopStatus;
import com.levi.route.api.response.Response;
import com.levi.route.api.services.RouteService;
import com.levi.route.api.services.StopService;

@RestController
@RequestMapping("/api/routes")
@CrossOrigin(origins = "*")
public class RouteController {
	
	private static final Logger log = LoggerFactory.getLogger(RouteController.class);
	
	@Autowired
	private RouteService routeService;
	
	@Autowired
	private StopService stopService;
	
	public RouteController() {
		
	}
	
	@PostMapping
	public ResponseEntity<Response<RouteDto>> addRoute(@Valid @RequestBody RouteDto routeDto,
			BindingResult result) throws ParseException {
		log.info("create route: {}", routeDto.toString());
		Response<RouteDto> response = new Response<RouteDto>();
		
		Route route = this.convertDtoToRoute(routeDto, result);

		route.setRouteStatus(RouteStatus.PENDING);
		route = this.routeService.persist(route);
	
		for(int i = 0;i<route.getPlannedStops().size();++i) {
			route.getPlannedStops().get(i).setRoute(route);
			route.getPlannedStops().get(i).setStopStatus(StopStatus.PENDING);
			stopService.persist(route.getPlannedStops().get(i));
		}
		
		response.setData(this.convertRouteToDto(route));
		return ResponseEntity.ok(response);
	}
	
	@PostMapping(value = "/routesByStatusInDate")
	public ResponseEntity<Response<List<RouteDto>>> findRoutesByStatusInDate(@Valid @RequestBody String date,
			BindingResult result) throws ParseException {
		log.info("Finding routes by status in {}", date.toString());
		Response<List<RouteDto>> response = new Response<List<RouteDto>>();
		
		List<Route> routes = this.routeService.findRoutesByStatusInDate(date);
		
	    List<RouteDto> routesDtos = new ArrayList<>();
	    
	    for(int i = 0;i<routes.size();++i) {
	    	routesDtos.add(convertRouteToDto(routes.get(i)));
	    }
	    response.setData(routesDtos);
		
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Response<String>> remove(@PathVariable("id") Long id) {
		log.info("Removing route ID: {}", id);
		this.routeService.remove(id);
		return ResponseEntity.ok(new Response<String>());
	}
	
	
	private Route convertDtoToRoute(RouteDto routeDto, BindingResult result) throws ParseException {
		Route route = new Route();
		
		route.setAssignedVehicle(Long.valueOf(routeDto.getAssignedVehicle()));
		route.setRoutePlan(routeDto.getRoutePlan());
		route.setPlannedStops(routeDto.getPlannedStops());
		
		return route;
	}
	
	private RouteDto convertRouteToDto(Route route) {
		RouteDto routeDto = new RouteDto();
		
		routeDto.setAssignedVehicle(String.valueOf((route.getAssignedVehicle())));
		routeDto.setId(route.getId());
		routeDto.setPlannedStops(route.getPlannedStops());
		routeDto.setRoutePlan(route.getRoutePlan());
		
		return routeDto;
	}

}
