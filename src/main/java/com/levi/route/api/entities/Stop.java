package com.levi.route.api.entities;

import java.util.Date;

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

import com.levi.route.api.enums.StopStatus;

@Entity
@Table(name = "stop")
public class Stop {

	private Long id;
	private double lat;
	private double lng;
	private String description;
	private double deliveryRadius;
	private Route route;
	private StopStatus stopStatus;
	private Date updateStatusProgressDate;
	private Date updateStatusFinishedDate;
	
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
	@Column(name = "stop_status", nullable = false)
	public StopStatus getStopStatus() {
		return stopStatus;
	}

	public void setStopStatus(StopStatus stopStatus) {
		this.stopStatus = stopStatus;
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
