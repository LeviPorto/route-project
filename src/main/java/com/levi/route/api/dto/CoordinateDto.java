package com.levi.route.api.dto;

import java.time.Instant;

import javax.validation.constraints.NotNull;

public class CoordinateDto {

	private double lat;
	private double lng;
	@NotNull
	private Instant instant;
	@NotNull
	private Long vehicleId;

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public Instant getInstant() {
		return instant;
	}

	public void setInstant(Instant instant) {
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
		return "CoordinateDto [lat=" + lat + ", lng=" + lng + ", instant=" + instant
				+ ", vehicleId=" + vehicleId + "]";
	}
	
}
