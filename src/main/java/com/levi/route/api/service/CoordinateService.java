package com.levi.route.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.levi.route.api.entity.Coordinate;

public interface CoordinateService {

	Page<Coordinate> receiveVehicleCoordinates(Long vehicleId,  PageRequest pageRequest);
	
}
