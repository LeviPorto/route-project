package com.levi.route.api.controller;

import java.text.ParseException;
import java.util.Date;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.levi.route.api.dto.CoordinateDto;
import com.levi.route.api.entity.Coordinate;
import com.levi.route.api.response.Response;
import com.levi.route.api.service.CoordinateProcessorService;
import com.levi.route.api.service.CoordinateService;
import com.levi.route.api.util.DateUtil;

@RestController
@RequestMapping("/route_processor/coordinate")
@CrossOrigin(origins = "*")
public class CoordinateController {

private static final Logger log = LoggerFactory.getLogger(RouteController.class);
	
	@Autowired
	private CoordinateService coordinateService;
	
	@Autowired
	private CoordinateProcessorService processor;
	
	public CoordinateController() {
		
	}
	
	@PostMapping
	public ResponseEntity<Response<CoordinateDto>> create(
			@Valid @RequestBody CoordinateDto coordinateDto) throws ParseException {
		
		log.info("Creating coordinate and process");
		Response<CoordinateDto> response = new Response<CoordinateDto>();;
		
		Coordinate coordinate = coordinateService.persist(convertDtoToCoordinate(coordinateDto));
		
		processor.processCoordinate(coordinate.getVehicleId());
		
		response.setData(convertCoordinateToDto(coordinate));
		
		return ResponseEntity.ok(response);
	}
	
	private Coordinate convertDtoToCoordinate(CoordinateDto coordinateDto) {
		Coordinate coordinate = new Coordinate();
		
		coordinate.setInstant(new Date());
		coordinate.setLat(Double.parseDouble(coordinateDto.getLat()));
		coordinate.setLng(Double.parseDouble(coordinateDto.getLng()));
		coordinate.setVehicleId(coordinateDto.getVehicleId());

		return coordinate;
	}
	
	private CoordinateDto convertCoordinateToDto(Coordinate coordinate) {
		CoordinateDto coordinateDto = new CoordinateDto();
		
		coordinateDto.setId(coordinate.getId());
		coordinateDto.setInstant(DateUtil.sdf.format(coordinate.getInstant()));
		coordinateDto.setLat(String.valueOf(coordinate.getLat()));
		coordinateDto.setLng(String.valueOf(coordinate.getLng()));
		coordinateDto.setVehicleId(coordinate.getVehicleId());
		

		return coordinateDto;
	}
}
