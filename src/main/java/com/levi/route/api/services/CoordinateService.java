package com.levi.route.api.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.levi.route.api.entities.Coordinate;

public interface CoordinateService {

	Page<Coordinate> receiveVehicleCoordinates(Long vehicleId,  PageRequest pageRequest);
	
}
