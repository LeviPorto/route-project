package com.levi.route.api.dtos;

import java.util.List;

import com.levi.route.api.entities.Stop;

public class RouteDto {

	private Long id;
	private String routePlan;
	private String assignedVehicle;
	private String routeStatus;
	private List<Stop> plannedStops;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getRoutePlan() {
		return routePlan;
	}

	public void setRoutePlan(String routePlan) {
		this.routePlan = routePlan;
	}

	public String getAssignedVehicle() {
		return assignedVehicle;
	}

	public void setAssignedVehicle(String assignedVehicle) {
		this.assignedVehicle = assignedVehicle;
	}

	public String getRouteStatus() {
		return routeStatus;
	}

	public void setRouteStatus(String routeStatus) {
		this.routeStatus = routeStatus;
	}

	public List<Stop> getPlannedStops() {
		return plannedStops;
	}

	public void setPlannedStops(List<Stop> plannedStops) {
		this.plannedStops = plannedStops;
	}

	@Override
	public String toString() {
		return "RouteDto [id=" + id + ", routePlan=" + routePlan + ", assignedVehicle=" + assignedVehicle+"]";
	}
	
	
}
