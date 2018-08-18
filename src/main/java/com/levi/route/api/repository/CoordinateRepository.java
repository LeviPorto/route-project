package com.levi.route.api.repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.levi.route.api.entity.Coordinate;

@Transactional(readOnly = true)
public interface CoordinateRepository extends JpaRepository<Coordinate, Long>  {

	@Query(value = "SELECT * FROM Coordinate c WHERE c.vehicle_id = :vehicle_id ORDER BY c.id DESC LIMIT 2", nativeQuery = true)
	List<Coordinate> findLastTop2ByVehicle(@Param("vehicle_id") Long vehicleId);
	
	@Query(value = "SELECT * FROM Coordinate c WHERE c.vehicle_id = :vehicle_id AND c.instant < :instant ORDER BY c.instant DESC LIMIT 1", nativeQuery = true)
	Optional<Coordinate> findPreviousCoordinate(@Param("vehicle_id") long vehicleId, @Param("instant") Instant instant);
}
