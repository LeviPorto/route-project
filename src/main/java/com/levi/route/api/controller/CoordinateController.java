package com.levi.route.api.controller;

import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.levi.route.api.dto.CoordinateDto;
import com.levi.route.api.entity.Coordinate;
import com.levi.route.api.response.Response;
import com.levi.route.api.service.CoordinateService;
import com.levi.route.api.service.impl.CoordinateProcessorService;

@RestController
@RequestMapping("/route_processor/coordinate")
@CrossOrigin(origins = "*")
public class CoordinateController {

private static final Logger log = LoggerFactory.getLogger(RouteController.class);
	
	@Autowired
	private CoordinateService coordinateService;
	
	private CoordinateProcessorService processor;
	
	public CoordinateController() {
		
	}
	
	@SuppressWarnings("deprecation")
	@PostMapping
	public ResponseEntity<Response<Page<CoordinateDto>>> create(
			@PathVariable("vehicleId") Long vehicleId, @RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "DESC") String dir) throws ParseException {
		
		log.info("Finding coordinate for vehicleId: {}", vehicleId);
		Response<Page<CoordinateDto>> response = new Response<Page<CoordinateDto>>();

		PageRequest pageRequest = new PageRequest(pag, 2, Direction.valueOf(dir), ord);
		Page<Coordinate> coordinates = this.coordinateService.receiveVehicleCoordinates(vehicleId, pageRequest);
		
		processor.processCoordinate(coordinates.getContent().get(0), coordinates.getContent().get(1), vehicleId);
		
		Page<CoordinateDto> coordinateDto = coordinates.map(coordinate -> this.convertCoordinateToDto(coordinate));
		response.setData(coordinateDto);
		
		return ResponseEntity.ok(response);
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
