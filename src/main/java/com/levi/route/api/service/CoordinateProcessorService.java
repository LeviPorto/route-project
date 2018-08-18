package com.levi.route.api.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.levi.route.api.entity.Coordinate;
import com.levi.route.api.entity.Route;
import com.levi.route.api.entity.Stop;
import com.levi.route.api.enun.RouteStatus;
import com.levi.route.api.enun.StopStatus;
import com.levi.route.api.service.RouteService;
import com.levi.route.api.service.StopService;
import com.levi.route.api.util.DateUtil;
import com.levi.route.api.util.HaversinCalculatorUtil;

@Service
public class CoordinateProcessorService {

    @Autowired
    private RouteService routeService;
    
    @Autowired
    private StopService stopService;
    
    @Autowired
    private CoordinateService coordinateService;

    @Async
    public void processCoordinate(Long vehicleId) throws ParseException {
    	List<Coordinate> coordinates = this.coordinateService.findLastTop2ByVehicle(vehicleId);
    	List<Route> routes = this.routeService.findPendingOrProgress();
    	
    	Coordinate lastCoordinate = coordinates.get(0);
    	Coordinate penultimateCoordinate;
    	if(coordinates.size() == 2) {
    		penultimateCoordinate = coordinates.get(1);
    	} else {
    		penultimateCoordinate = new Coordinate();
    	}
    	
		for(int i = 0;i<routes.size();++i) {
			Route route = routes.get(i);
			
			if(lastCoordinate.getVehicleId().equals(route.getAssignedVehicle())) {
				changeRouteStatusToProgress(lastCoordinate, route);
				
				int countOfFinishedStopByRoute = 0;
				for(int y = 0;y<route.getPlannedStops().size();++y) {
					Stop stop = route.getPlannedStops().get(y);
					
					if(penultimateCoordinate.getInstant() != null) {
						changeStopStatusToProgress(lastCoordinate, penultimateCoordinate, stop);
					}
					changeStopStatusToFinished(lastCoordinate, stop);
					
					if(stop.getStopStatus().toString().equals("FINISHED")) {
						countOfFinishedStopByRoute++;
					}
					
				}
			
				changeRouteStatusToFinished(route, countOfFinishedStopByRoute);
			}
			
		}
    }
    
    private void changeRouteStatusToFinished(Route route, int countOfFinishedStopByRoute) {
		if(countOfFinishedStopByRoute == route.getPlannedStops().size()) {
			
			if(!route.getStatus().equals(RouteStatus.FINISHED)) {
				route.setStatus(RouteStatus.FINISHED);
				route.setUpdateStatusFinishedDate(new Date());
				routeService.persist(route);
			}
			
		}
	}

	private void changeStopStatusToFinished(Coordinate lastCoordinate, Stop stop) {
		if((HaversinCalculatorUtil.distanceBetweenTwoPoints(lastCoordinate.getLat()
			, lastCoordinate.getLng(),stop.getLat(), stop.getLng()) > stop.getDeliveryRadius())) {
			
			if(!stop.getStopStatus().equals(StopStatus.FINISHED)) {
				stop.setStopStatus(StopStatus.FINISHED);
				stop.setUpdateStatusFinishedDate(new Date());
				stopService.persist(stop);
			}
			
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
				
				if(!stop.getStopStatus().equals(StopStatus.PROGRESS)) {
					stop.setStopStatus(StopStatus.PROGRESS);
					stop.setUpdateStatusProgressDate(new Date());
					stopService.persist(stop);
				}
				
			}
			
		}
	}

	private void changeRouteStatusToProgress(Coordinate lastCoordinate, Route route) {
		if(lastCoordinate.getVehicleId().equals(route.getAssignedVehicle())) {
			
			if(!route.getStatus().equals(RouteStatus.PROGRESS)) {

				route.setStatus(RouteStatus.PROGRESS);
				route.setUpdateStatusProgressDate(new Date());
				routeService.persist(route);
				
			}
		}
	}
}
