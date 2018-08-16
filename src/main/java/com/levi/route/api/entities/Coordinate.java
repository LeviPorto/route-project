package com.levi.route.api.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "coordinate")
public class Coordinate {
	
	private Long id;
	private double lat;
	private double lng;
	private Date instant;
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

	@Column(name = "instant", nullable = false)
	public Date getInstant() {
		return instant;
	}

	public void setInstant(Date instant) {
		this.instant = instant;
	}
	
}
