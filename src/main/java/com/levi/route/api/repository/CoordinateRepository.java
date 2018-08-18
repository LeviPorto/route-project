package com.levi.route.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.levi.route.api.entity.Coordinate;

@Transactional(readOnly = true)
public interface CoordinateRepository extends JpaRepository<Coordinate, Long>  {

	@Query("SELECT c FROM Coordinate c WHERE c.vehicleId = :vehicleId")
	Page<Coordinate> findTop2ByVehicleId(@Param("vehicleId") Long vehicleId,  Pageable pageable);
	
	
	
}
