package com.levi.route.api.entity;

import java.time.Instant;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.levi.route.api.dto.CoordinateDto;

@Entity
@Table(name = "coordinate")
public class Coordinate {
	
	private Long id;
	private double lat;
	private double lng;
	private Instant instant;
	private Long vehicleId;
	
	public Coordinate() {
		
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
	
	@Column(name = "vehicle_id", nullable = false)
	public Long getVehicleId() {
		return vehicleId;
	}
	
	public void setVehicleId(Long vehicleId) {
		this.vehicleId = vehicleId;
	}

	public Instant getInstant() {
		return instant;
	}

	public void setInstant(Instant instant) {
		this.instant = instant;
	}
	
	public static Coordinate fromDto(CoordinateDto coordinateDto) {
		Coordinate coordinate = new Coordinate();
		
		coordinate.setInstant(Instant.now());
		coordinate.setLat(coordinateDto.getLat());
		coordinate.setLng(coordinateDto.getLng());
		coordinate.setVehicleId(coordinateDto.getVehicleId());

		return coordinate;
	}
	
}
