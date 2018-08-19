package com.levi.route.api.dto;

import java.time.Instant;

import javax.validation.constraints.NotNull;

public class CoordinateDto {

	private String lat;
	private String lng;
	private String instant;
	private String vehicleId;

	

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



	public String getVehicleId() {
		return vehicleId;
	}



	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}



	@Override
	public String toString() {
		return "CoordinateDto [lat=" + lat + ", lng=" + lng + ", instant=" + instant
				+ ", vehicleId=" + vehicleId + "]";
	}
	
}
