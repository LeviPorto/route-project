package com.levi.route.api.controller;

import java.text.ParseException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.levi.route.api.dto.RouteDto;
import com.levi.route.api.dto.StopDto;
import com.levi.route.api.entity.Route;
import com.levi.route.api.entity.Stop;
import com.levi.route.api.enun.RouteStatus;
import com.levi.route.api.enun.StopStatus;
import com.levi.route.api.response.Response;
import com.levi.route.api.service.RouteService;
import com.levi.route.api.service.StopService;

@RestController
@RequestMapping("/route_processor/route")
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
	public ResponseEntity<Response<RouteDto>> create(@Valid @RequestBody RouteDto routeDto,
			BindingResult result) throws ParseException {
		log.info("create route: {}", routeDto.toString());
		Response<RouteDto> response = new Response<RouteDto>();
		
		Route route = this.convertDtoToRoute(routeDto, result);

		route.setStatus(RouteStatus.PENDING);
		route = this.routeService.persist(route);
	
		for(int i = 0;i<route.getPlannedStops().size();++i) {
			route.getPlannedStops().get(i).setRoute(route);
			route.getPlannedStops().get(i).setStopStatus(StopStatus.PENDING);
			stopService.persist(route.getPlannedStops().get(i));
		}
		
		response.setData(this.convertRouteToDto(route));
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Response<String>> remove(@PathVariable("id") Long id) {
		log.info("Removing route ID: {}", id);
		this.routeService.remove(id);
		return ResponseEntity.ok(new Response<String>());
	}
	
	@GetMapping(value = "/statusInDate/{routeId}")
	public ResponseEntity<Response<String>> findRoutesByStatusInDate( 
			@ModelAttribute @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date date,
			BindingResult result,
			@PathVariable Long routeId
			) throws ParseException {
		log.info("Finding routes by status in {}", date.toString());
		Response<String> response = new Response<String>();
		
		String status = this.routeService.findStatusInDate(date, routeId);
	    
	    response.setData(status);
	
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/longerStopsByRoute/{routeId}")
	public ResponseEntity<Response<List<StopDto>>> findLongerStopsByRoute(@PathVariable Long routeId)  {
		
		log.info("Finding longer stop by route {}");
		Response<List<StopDto>> response = new Response<List<StopDto>>();
		
		List<Stop> stops = stopService.findLongerStopsByRoute(routeId);
		List<StopDto> stopDtos = new ArrayList<>();
		
	    for(int i = 0;i<stops.size();++i) {
	    	stopDtos.add(convertStopToDto(stops.get(i)));
	    }
		
	    response.setData(stopDtos);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/finishedStopsByRoute/{routeId}")
	public ResponseEntity<Response<List<StopDto>>> findFinishedStopByRoute(
			@PathVariable Long routeId,
			@RequestParam Instant date)  {
		
		log.info("Calculating finished stops by route in: {}", date);
		Response<List<StopDto>> response = new Response<List<StopDto>>();
		
	    List<Stop> stops = stopService.findFinishedStopsByRoute(Date.from(date), routeId);
	    List<StopDto> stopDtos = new ArrayList<>();
	    
	    for(int i = 0;i<stops.size();++i) {
	    	stopDtos.add(convertStopToDto(stops.get(i)));
	    }
	    
	    response.setData(stopDtos);
		return ResponseEntity.ok(response);
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
		routeDto.setStatus(route.getStatus());
		
		return routeDto;
	}
	
	private StopDto convertStopToDto(Stop stop) {
		StopDto stopDto = new StopDto();
		
		stopDto.setId(stop.getId());
		stopDto.setLat(String.valueOf(stop.getLat()));
		stopDto.setLng(String.valueOf(stop.getLng()));
		stopDto.setDescription(stop.getDescription());
		stopDto.setDeliveryRadius(String.valueOf(stop.getDeliveryRadius()));
		stopDto.setRouteId(String.valueOf(stop.getRoute().getId()));
		stopDto.setStatus(stop.getStopStatus());
		
		return stopDto;
	}

}
