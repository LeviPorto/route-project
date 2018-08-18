package com.levi.route.api.dto;

public class StopDto {

	private Long id;
	private String lat;
	private String lng;
	private String description;
	private String deliveryRadius;
	private String routeId;
	private String stopStatus;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

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

	public String getDeliveryRadius() {
		return deliveryRadius;
	}

	public void setDeliveryRadius(String deliveryRadius) {
		this.deliveryRadius = deliveryRadius;
	}

	public String getRouteId() {
		return routeId;
	}

	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}

	public String getStopStatus() {
		return stopStatus;
	}

	public void setStopStatus(String stopStatus) {
		this.stopStatus = stopStatus;
	}

	@Override
	public String toString() {
		return "StopDto [id=" + id + ", lat=" + lat + ", lng=" + lng + ", description=" + description
				+ ", deliveryRadius=" + deliveryRadius + ", routeId=" + routeId + "]";
	}
	
	
}
