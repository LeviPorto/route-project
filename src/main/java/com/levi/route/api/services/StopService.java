package com.levi.route.api.services;

import java.util.List;

import com.levi.route.api.entities.Stop;

public interface StopService {

	Stop persist(Stop stop);
	
	List<Stop> findFinishedStopByRoute(String date);
	
	List<Stop> findLongerStopByRoute();
	
}
