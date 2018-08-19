package com.levi.route.api.entity;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.levi.route.api.dto.StopDto;
import com.levi.route.api.enun.StopStatus;

@Entity
@Table(name = "stop")
public class Stop implements GeoPoint {

	private Long id;
	private double lat;
	private double lng;
	private String description;
	private double deliveryRadius;
	private Route route;
	private StopStatus stopStatus;
	private Instant startDate;
	private Instant endDate;
	
	public Stop() {
		
	}
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "latitude", nullable = false)
	public double getLat() {
		return lat;
	}
	
	public void setLat(double lat) {
		this.lat = lat;
	}
	
	@Column(name = "longitude", nullable = false)
	public double getLng() {
		return lng;
	}
	
	public void setLng(double lng) {
		this.lng = lng;
	}
	
	@Column(name = "description", nullable = false)
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name = "delivery_radius", nullable = false)
	public double getDeliveryRadius() {
		return deliveryRadius;
	}
	
	public void setDeliveryRadius(double deliveryRadius) {
		this.deliveryRadius = deliveryRadius;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_route")
	public Route getRoute() {
		return route;
	}

	public void setRoute(Route route) {
		this.route = route;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "stop_status", nullable = true)
	public StopStatus getStopStatus() {
		return stopStatus;
	}

	public void setStopStatus(StopStatus stopStatus) {
		this.stopStatus = stopStatus;
	}

	@Column(name = "start_date", nullable = true)
	public Instant getStartDate() {
		return startDate;
	}

	public void setStartDate(Instant startDate) {
		this.startDate = startDate;
	}

	@Column(name = "end_date", nullable = true)
	public Instant getEndDate() {
		return endDate;
	}

	public void setEndDate(Instant endDate) {
		this.endDate = endDate;
	}

	public static StopDto toDto(Stop stop) {
		StopDto stopDto = new StopDto();
		
		stopDto.setId(stop.getId());
		stopDto.setLat(String.valueOf(stop.getLat()));
		stopDto.setLng(String.valueOf(stop.getLng()));
		stopDto.setDescription(stop.getDescription());
		stopDto.setDeliveryRadius(String.valueOf(stop.getDeliveryRadius()));
		stopDto.setRouteId(String.valueOf(stop.getRoute().getId()));
		stopDto.setStatus(stop.getStopStatus());
		
		return stopDto;
	}
	
	public static Stop fromDto(StopDto stopDto) {
		Stop stop = new Stop();
		Route route = new Route();
		stop.setRoute(route);
		stop.getRoute().setId(Long.valueOf(stopDto.getRouteId()));
		stop.setLat(Double.valueOf(stopDto.getLat()));
		stop.setLng(Double.valueOf(stopDto.getLng()));
		stop.setDescription(stopDto.getDescription());
		stop.setDeliveryRadius(Double.valueOf(stopDto.getDeliveryRadius()));
		stop.setStopStatus(stopDto.getStatus());
		
		return stop;
	}
	
}
