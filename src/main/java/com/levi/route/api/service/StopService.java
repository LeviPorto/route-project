package com.levi.route.api.service;

import java.util.List;

import com.levi.route.api.entity.Stop;

public interface StopService {

	Stop persist(Stop stop);
	
	List<Stop> findFinishedStopByRoute(String date);
	
	List<Stop> findLongerStopByRoute();
	
	void remove(Long id);
	
}
