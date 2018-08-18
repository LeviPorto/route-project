package com.levi.route.api.dto;


public class CoordinateDto {

	private Long id;
	private String lat;
	private String lng;
	private String instant;
	private Long vehicleId;
	
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

	public String getInstant() {
		return instant;
	}

	public void setInstant(String instant) {
		this.instant = instant;
	}

	public Long getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(Long vehicleId) {
		this.vehicleId = vehicleId;
	}

	@Override
	public String toString() {
		return "CoordinateDto [id=" + id + ", lat=" + lat + ", lng=" + lng + ", instant=" + instant
				+ ", vehicleId=" + vehicleId + "]";
	}
	
}
