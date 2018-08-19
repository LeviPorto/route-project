package com.levi.route.api.dto;

import javax.validation.constraints.NotNull;

import com.levi.route.api.enun.StopStatus;

public class StopDto {

	private Long id;
	private String lat;
	private String lng;
	private String description;
	private String deliveryRadius;
	private String routeId;
	private StopStatus status;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@NotNull
	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	@NotNull
	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@NotNull
	public String getDeliveryRadius() {
		return deliveryRadius;
	}

	public void setDeliveryRadius(String deliveryRadius) {
		this.deliveryRadius = deliveryRadius;
	}

	@NotNull
	public String getRouteId() {
		return routeId;
	}

	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}

	public StopStatus getStatus() {
		return status;
	}

	public void setStatus(StopStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "StopDto [id=" + id + ", lat=" + lat + ", lng=" + lng + ", description=" + description
				+ ", deliveryRadius=" + deliveryRadius + ", routeId=" + routeId + "]";
	}
	
}
