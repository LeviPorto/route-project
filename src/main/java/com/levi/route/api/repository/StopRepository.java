package com.levi.route.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.levi.route.api.entity.Stop;

public interface StopRepository extends JpaRepository<Stop, Long> {

	@Query("SELECT s FROM Stop s WHERE update_status_finished_date <= :date ORDER BY id_route")
	List<Stop> findFinishedStopByRoute(@Param("date") String date);
	
	@Query(value = "Select * From Stop AS s WHERE s.stop_status = 'FINISHED'"
			+ " AND DATEDIFF(update_status_finished_date, update_status_progress_date)"
			+ " IN (SELECT max(DATEDIFF(update_status_finished_date, update_status_progress_date))"
			+ " FROM stop GROUP BY id_route) ORDER BY id_route",
			nativeQuery = true)
	List<Stop> findLongerStopByRoute();
	
}
