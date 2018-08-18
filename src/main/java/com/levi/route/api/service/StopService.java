package com.levi.route.api.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.levi.route.api.entity.Stop;
import com.levi.route.api.repository.StopRepository;
import com.levi.route.api.service.StopService;

@Service
public class StopService {

	@Autowired
	private StopRepository stopRepository;

	private static final Logger log = LoggerFactory.getLogger(CoordinateService.class);
	
	public Stop persist(Stop stop) {
		log.info("Creating or updating stop: {}", stop);
		return this.stopRepository.save(stop);
	}

	public void remove(Long id) {
		log.info("Removing stop ID {}", id);
		this.stopRepository.deleteById(id);
	}
	
	public List<Stop> findFinishedStopsByRoute(Date date, Long routeId) {
		log.info("Finding finished stops by date {}", date);
		return this.stopRepository.findFinishedStopsByRoute(date, routeId);
	}

	public List<Stop> findLongerStopsByRoute(Long routeId) {
		log.info("Finding longer stop by route {}");
		return this.stopRepository.findLongerStopsByRoute(routeId);
	}

}
