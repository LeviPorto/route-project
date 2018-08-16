package com.levi.route.api.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.levi.route.api.entities.Coordinate;
import com.levi.route.api.repositories.CoordinateRepository;
import com.levi.route.api.services.CoordinateService;

@Service
public class CoordinateServiceImpl implements CoordinateService {
	
	@Autowired
	private CoordinateRepository coordinateRepository;

	private static final Logger log = LoggerFactory.getLogger(CoordinateServiceImpl.class);

	@Override
	public Page<Coordinate> receiveVehicleCoordinates(Long vehicleId, PageRequest pageRequest) {
		log.info("Finding coordinates for idVehicle {}", vehicleId);
		return this.coordinateRepository.findTop2ByVehicleId(vehicleId, pageRequest);
	}

	

}
