package com.levi.route.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.query.Param;

import com.levi.route.api.entity.Route;

public interface RouteService {

	Route persist(Route route);

	List<Route> findPendentOrProgressByVehicleId(Long vehicleId);
	
	List<Route> findRoutesByStatusInDate(@Param("date") String date);
	
	void remove(Long id);
}
