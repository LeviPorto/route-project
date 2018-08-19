package com.levi.route.api.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.levi.route.api.dto.CoordinateDto;
import com.levi.route.api.entity.Coordinate;
import com.levi.route.api.service.CoordinateProcessorService;
import com.levi.route.api.service.CoordinateService;

@RestController
@RequestMapping("/routeProcessor/coordinate")
@CrossOrigin(origins = "*")
public class CoordinateController {

private static final Logger log = LoggerFactory.getLogger(RouteController.class);
	
	@Autowired
	private CoordinateService coordinateService;
	
	@Autowired
	private CoordinateProcessorService processor;
	
	@PostMapping
	public CoordinateDto create(@Valid @RequestBody CoordinateDto coordinateDto) {
		log.info("Creating coordinate and process");
		Coordinate coordinate = coordinateService.persist(Coordinate.fromDto(coordinateDto));
		processor.processCoordinate(coordinate);
		return coordinateDto;
	}
	
}
