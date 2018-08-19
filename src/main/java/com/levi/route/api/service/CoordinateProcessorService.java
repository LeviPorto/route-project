package com.levi.route.api.service;

import com.levi.route.api.entity.Coordinate;
import com.levi.route.api.entity.Route;
import com.levi.route.api.entity.Stop;
import com.levi.route.api.enun.RouteStatus;
import com.levi.route.api.enun.StopStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static com.levi.route.api.util.DistanceCalculatorUtil.distance;

@Service
public class CoordinateProcessorService {

    @Autowired
    private RouteService routeService;

    @Autowired
    private StopService stopService;

    @Autowired
    private CoordinateService coordinateService;

    @Async
    public void processCoordinate(Coordinate coordinate) {
        List<Route> routes = this.routeService.findPendingOrProgress();
        routes.forEach(route -> {
            if (isRouteCoordinate(coordinate, route)) {
                processRoute(route, coordinate);
            }
        });
    }

    private void processRoute(Route route, Coordinate coordinate) {
        Optional<Coordinate> previousCoordinate = coordinateService.findPreviousCoordinate(coordinate.getVehicleId(), coordinate.getInstant());
        startRouteIfNecessary(route);
        for (Stop stop : route.getPlannedStops()) {
            if (previousCoordinate.isPresent()) {
            	if(!previousCoordinate.get().getId().equals(coordinate.getId())) {
            		processStop(coordinate, previousCoordinate.get(), stop);
            	}
            }
        }
        endRouteIfNecessary(route);
    }

    private void processStop(Coordinate coordinate, Coordinate previousCoordinate, Stop stop) {
        if (isPending(stop)) {
            startStopIfNecessary(coordinate, previousCoordinate, stop);
        } else if (isInProgress(stop)) {
            endStopIfNecessary(coordinate, stop);
        }
    }

    private boolean isRouteCoordinate(Coordinate coordinate, Route route) {
        return coordinate.getVehicleId().equals(route.getAssignedVehicle());
    }

    private void startRouteIfNecessary(Route route) {
        if (isPending(route)) {
            route.setStatus(RouteStatus.PROGRESS);
            route.setStartDate(Instant.now());
            routeService.persist(route);
        }
    }

    private void endRouteIfNecessary(Route route) {
        long finishedStops = route.getPlannedStops().stream()
                .filter(this::isFinished)
                .count();

        if (isInProgress(route) && finishedStops == route.getPlannedStops().size()) {
                route.setStatus(RouteStatus.FINISHED);
                route.setEndDate(Instant.now());
                routeService.persist(route);
        }
    }

    private void startStopIfNecessary(Coordinate coordinate, Coordinate previousCoordinate, Stop stop) {
        if (hasTwoCoordinatesWithinStop(coordinate, previousCoordinate, stop) &&
                hasStayedLongEnough(coordinate, previousCoordinate)) {

            stop.setStopStatus(StopStatus.PROGRESS);
            stop.setStartDate(Instant.now());
            stopService.persist(stop);
        }
    }

    private void endStopIfNecessary(Coordinate coordinate, Stop stop) {
        if (distance(coordinate, stop) > stop.getDeliveryRadius()) {

            stop.setStopStatus(StopStatus.FINISHED);
            stop.setEndDate(Instant.now());
            stopService.persist(stop);
        }
    }

    private boolean hasStayedLongEnough(Coordinate coordinate, Coordinate previousCoordinate) {
        return Duration.between(previousCoordinate.getInstant(), coordinate.getInstant()).toMinutes() <= 5;
    }

    private boolean hasTwoCoordinatesWithinStop(Coordinate coordinate, Coordinate previousCoordinate, Stop stop) {
        return isCoordinateWithinStop(coordinate, stop) && isCoordinateWithinStop(previousCoordinate, stop);
    }

    private boolean isCoordinateWithinStop(Coordinate coordinate, Stop stop) {
        return distance(coordinate, stop) <= stop.getDeliveryRadius();
    }

    private boolean isPending(Route route) {
        return RouteStatus.PENDING.equals(route.getStatus());
    }

    private boolean isInProgress(Route route) {
        return RouteStatus.PROGRESS.equals(route.getStatus());
    }

    private boolean isInProgress(Stop stop) {
        return StopStatus.PROGRESS.equals(stop.getStopStatus());
    }

    private boolean isPending(Stop stop) {
        return StopStatus.PENDING.equals(stop.getStopStatus());
    }

    private boolean isFinished(Stop stop) {
        return StopStatus.FINISHED.equals(stop.getStopStatus());
    }
}
