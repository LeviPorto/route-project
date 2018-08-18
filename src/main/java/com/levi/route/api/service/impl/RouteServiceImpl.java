package com.levi.route.api.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.levi.route.api.entity.Route;
import com.levi.route.api.repository.RouteRepository;
import com.levi.route.api.service.RouteService;

@Service
public class RouteServiceImpl implements RouteService {

	@Autowired
	private RouteRepository routeRepository;

	private static final Logger log = LoggerFactory.getLogger(RouteServiceImpl.class);
	
	@Override
	public Route persist(Route route) {
		log.info("Creating or updating route: {}", route);
		return this.routeRepository.save(route);
	}

	@Override
	public List<Route> findPendentOrProgressByVehicleId(Long vehicleId) {
		log.info("Finding pendent route for vehicleId {}", vehicleId);
		return this.routeRepository.findPendentOrProgressByVehicleId(vehicleId);
	}

	@Override
	public List<Route> findRoutesByStatusInDate(String date) {
		log.info("Finding routes by status in {}", date);
		return this.routeRepository.findRoutesByStatusInDate(date);
	}
	
	@Override
	public void remove(Long id) {
		log.info("Removing route ID {}", id);
		this.routeRepository.deleteById(id);
	}

}
