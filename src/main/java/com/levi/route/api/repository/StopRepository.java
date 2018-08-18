package com.levi.route.api.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.levi.route.api.entity.Stop;

public interface StopRepository extends JpaRepository<Stop, Long> {

	@Query("SELECT s FROM Stop s WHERE update_status_finished_date <= :date AND id_route = :id_route")
	List<Stop> findFinishedStopsByRoute(@Param("date") Date date, @Param("id_route") Long routeId);
	
	@Query(value = "Select * From Stop AS s WHERE s.stop_status = 'FINISHED' AND s.id_route = :id_route"
			+ " ORDER BY (s.update_status_finished_date - s.update_status_progress_date) DESC"
			,nativeQuery = true)
	List<Stop> findLongerStopsByRoute(@Param("id_route") Long routeId);
}
