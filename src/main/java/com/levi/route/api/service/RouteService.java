package com.levi.route.api.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.levi.route.api.entity.Route;
import com.levi.route.api.repository.RouteRepository;
import com.levi.route.api.service.RouteService;

@Service
public class RouteService {

	@Autowired
	private RouteRepository routeRepository;

	private static final Logger log = LoggerFactory.getLogger(RouteService.class);
	
	public Route persist(Route route) {
		log.info("Creating or updating route: {}", route);
		return this.routeRepository.save(route);
	}

	public List<Route> findPendingOrProgress() {
		log.info("Finding pending route ");
		return this.routeRepository.findPendingOrProgress();
	}

	public String findStatusInDate(String date, Long routeId) {
		log.info("Finding routes by status in {}", date);
		return this.routeRepository.findStatusInDate(date, routeId);
	}
	
	public void remove(Long id) {
		log.info("Removing route ID {}", id);
		this.routeRepository.deleteById(id);
	}

}
