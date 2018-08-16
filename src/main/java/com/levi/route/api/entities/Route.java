package com.levi.route.api.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.levi.route.api.enums.RouteStatus;

@Entity
@Table(name = "route")
public class Route {
	
	private Long id;
	private RouteStatus routeStatus;
	private Long assignedVehicle;
	private List<Stop> plannedStops;
	private String routePlan;
	private Date updateStatusProgressDate;
	private Date updateStatusFinishedDate;
	
	public Route() {
		
	}
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "assigned_vehicle", nullable = false)
	public Long getAssignedVehicle() {
		return assignedVehicle;
	}

	public void setAssignedVehicle(Long assignedVehicle) {
		this.assignedVehicle = assignedVehicle;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "route_status", nullable = true)
	public RouteStatus getRouteStatus() {
		return routeStatus;
	}

	public void setRouteStatus(RouteStatus routeStatus) {
		this.routeStatus = routeStatus;
	}

	@OneToMany(mappedBy = "route", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnoreProperties("route")
	public List<Stop> getPlannedStops() {
		return plannedStops;
	}

	public void setPlannedStops(List<Stop> plannedStops) {
		this.plannedStops = plannedStops;
	}

	@Column(name = "route_plan", nullable = false)
	public String getRoutePlan() {
		return routePlan;
	}

	public void setRoutePlan(String routePlan) {
		this.routePlan = routePlan;
	}

	@Column(name = "update_status_progress_date", nullable = true)
	public Date getUpdateStatusProgressDate() {
		return updateStatusProgressDate;
	}

	public void setUpdateStatusProgressDate(Date updateStatusProgressDate) {
		this.updateStatusProgressDate = updateStatusProgressDate;
	}

	@Column(name = "update_status_finished_date", nullable = true)
	public Date getUpdateStatusFinishedDate() {
		return updateStatusFinishedDate;
	}

	public void setUpdateStatusFinishedDate(Date updateStatusFinishedDate) {
		this.updateStatusFinishedDate = updateStatusFinishedDate;
	}
	
	
}
