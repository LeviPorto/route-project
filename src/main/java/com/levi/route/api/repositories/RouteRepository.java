package com.levi.route.api.repositories;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.levi.route.api.entities.Route;

public interface RouteRepository extends JpaRepository<Route, Long> {

	@Query("SELECT r FROM Route r WHERE r.assignedVehicle = :assignedVehicle"
			+ " AND (r.routeStatus = 'PENDING' OR r.routeStatus = 'PROGRESS')")
	Optional<List<Route>> findPendentOrProgressByVehicleId(@Param("assignedVehicle") Long assignedVehicle);
	
	@Query(value = "SELECT r.id ,r.assigned_vehicle, r.route_plan, r.update_status_finished_date,  r.update_status_progress_date,\r\n" + 
			"CASE \r\n" + 
			"	WHEN STR_TO_DATE(:date, '%Y-%m-%d %h:%i:%s') < r.update_status_progress_date OR r.update_status_progress_date IS NULL THEN 'PENDING'\r\n" + 
			"   WHEN (STR_TO_DATE(:date, '%Y-%m-%d %h:%i:%s') >= r.update_status_progress_date AND STR_TO_DATE(:date, '%Y-%m-%d %h:%i:%s') < r.update_status_finished_date) OR (r.update_status_finished_date IS NULL) THEN 'PROGRESS'\r\n" + 
			"   WHEN STR_TO_DATE(:date, '%Y-%m-%d %h:%i:%s') >= r.update_status_finished_date THEN 'FINISHED'\r\n" + 
			"END AS route_status\r\n" + 
			"from route AS r", nativeQuery = true)
	List<Route> findRoutesByStatusInDate(@Param("date") String date);
	
}
