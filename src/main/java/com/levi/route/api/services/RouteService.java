package com.levi.route.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.query.Param;

import com.levi.route.api.entities.Route;

public interface RouteService {

	Route persist(Route route);

	Optional<List<Route>> findPendentByVehicleId(Long vehicleId);
	
	List<Route> findRoutesByStatusInDate(@Param("date") String date);
	
	void remove(Long id);
}
