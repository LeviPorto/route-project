package com.levi.route.api.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.levi.route.api.entity.Stop;
import com.levi.route.api.repository.StopRepository;
import com.levi.route.api.service.StopService;

@Service
public class StopServiceImpl implements StopService {

	@Autowired
	private StopRepository stopRepository;

	private static final Logger log = LoggerFactory.getLogger(CoordinateServiceImpl.class);
	
	@Override
	public Stop persist(Stop stop) {
		log.info("Creating or updating stop: {}", stop);
		return this.stopRepository.save(stop);
	}

	@Override
	public List<Stop> findFinishedStopByRoute(String date) {
		log.info("Finding finished stops by date {}", date);
		return this.stopRepository.findFinishedStopByRoute(date);
	}

	@Override
	public List<Stop> findLongerStopByRoute() {
		log.info("Finding longer stop by route {}");
		return this.stopRepository.findLongerStopByRoute();
	}

	@Override
	public void remove(Long id) {
		log.info("Removing stop ID {}", id);
		this.stopRepository.deleteById(id);
	}

	

}
