package com.levi.route.api.service.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

import com.levi.route.api.entity.Coordinate;
import com.levi.route.api.entity.Route;
import com.levi.route.api.entity.Stop;
import com.levi.route.api.enun.RouteStatus;
import com.levi.route.api.enun.StopStatus;
import com.levi.route.api.service.RouteService;
import com.levi.route.api.service.StopService;
import com.levi.route.api.util.DateUtil;
import com.levi.route.api.util.HaversinCalculatorUtil;

public class CoordinateProcessorService {

    @Autowired
    private RouteService routeService;
    
    @Autowired
    private StopService stopService;

    @Async
    public void processCoordinate(Coordinate lastCoordinate, Coordinate penultimateCoordinate, Long vehicleId) throws ParseException {
    	List<Route> routes = this.routeService.findPendentOrProgressByVehicleId(vehicleId);
		
		for(int i = 0;i<routes.size();++i) {
			Route route = routes.get(i);
			
			changeRouteStatusToProgress(lastCoordinate, route);
			
			int countOfFinishedStopByRoute = 0;
			for(int y = 0;y<route.getPlannedStops().size();++y) {
				Stop stop = route.getPlannedStops().get(y);
				
				changeStopStatusToProgress(lastCoordinate, penultimateCoordinate, stop);
				changeStopStatusToFinished(lastCoordinate, stop);
				
				if(stop.getStopStatus().toString().equals("FINISHED")) {
					countOfFinishedStopByRoute++;
				}
				
			}
		
			changeRouteStatusToFinished(route, countOfFinishedStopByRoute);
		}
    }
    
    private void changeRouteStatusToFinished(Route route, int countOfFinishedStopByRoute) {
		if(countOfFinishedStopByRoute == route.getPlannedStops().size()) {
			route.setRouteStatus(RouteStatus.FINISHED);
			route.setUpdateStatusFinishedDate(new Date());
			routeService.persist(route);
		}
	}

	private void changeStopStatusToFinished(Coordinate lastCoordinate, Stop stop) {
		if(stop.getStopStatus().toString().equals("PROGRESS") && 
				(HaversinCalculatorUtil.distanceBetweenTwoPoints(lastCoordinate.getLat()
			, lastCoordinate.getLng(),stop.getLat(), stop.getLng()) > stop.getDeliveryRadius())) {
			stop.setStopStatus(StopStatus.FINISHED);
			stop.setUpdateStatusFinishedDate(new Date());
			stopService.persist(stop);
		}
	}

	private void changeStopStatusToProgress(Coordinate lastCoordinate, Coordinate penultimateCoordinate, Stop stop)
			throws ParseException {
		if((HaversinCalculatorUtil.distanceBetweenTwoPoints(lastCoordinate.getLat(), 
				lastCoordinate.getLng(),stop.getLat(),stop.getLng()) <= 
				stop.getDeliveryRadius()) && (HaversinCalculatorUtil.distanceBetweenTwoPoints(penultimateCoordinate.getLat()
				, penultimateCoordinate.getLng() ,stop.getLat(),stop.getLng()) <= 
				stop.getDeliveryRadius())){
			
			if(DateUtil.getDateDiff(penultimateCoordinate.getInstant(),
					lastCoordinate.getInstant(), TimeUnit.MINUTES) <= 5){
				
				stop.setStopStatus(StopStatus.PROGRESS);
				stop.setUpdateStatusProgressDate(new Date());
				stopService.persist(stop);
			}
			
		}
	}

	private void changeRouteStatusToProgress(Coordinate lastCoordinate, Route route) {
		if(lastCoordinate.getVehicleId().equals(route.getAssignedVehicle())) {
			route.setRouteStatus(RouteStatus.PROGRESS);
			route.setUpdateStatusProgressDate(new Date());
			routeService.persist(route);
		}
	}
}
