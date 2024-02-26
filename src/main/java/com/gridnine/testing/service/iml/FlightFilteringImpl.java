package com.gridnine.testing.service.iml;

import com.gridnine.testing.model.Flight;
import com.gridnine.testing.model.Segment;
import com.gridnine.testing.service.FlightFiltering;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FlightFilteringImpl implements FlightFiltering {
    private final List<Flight> flightList;

    public FlightFilteringImpl(List<Flight> flightList) {
        this.flightList = new ArrayList<>(flightList);
    }

    @Override
    public List<Flight> build() {
        return flightList;
    }

    @Override
    public FlightFiltering departureBeforeNow() {
        flightList.removeIf(flight ->
                flight.getSegments().stream()
                        .anyMatch(segment -> segment.getDepartureDate().isBefore(LocalDateTime.now())));
        return this;
    }

    @Override
    public FlightFiltering segmentsWithArrivalDateBeforeDepartureDate() {
        flightList.removeIf(flight ->
                flight.getSegments().stream()
                        .anyMatch(segment -> segment.getArrivalDate().isBefore(segment.getDepartureDate())));
        return this;
    }

    @Override
    public FlightFiltering flightsWhereTotalTimeOnTheGroundExceedsTwoHours() {
        flightList.removeIf(flight -> {
            List<Segment> segments = flight.getSegments();
            LocalDateTime currentDeparture;
            LocalDateTime lastArrival;
            Duration durationOnTheGround = Duration.ZERO;

            for (int i = 1; i < segments.size(); i++) {
                currentDeparture = segments.get(i).getDepartureDate();
                lastArrival = segments.get(i - 1).getArrivalDate();
                durationOnTheGround = durationOnTheGround.plus(Duration.between(currentDeparture, lastArrival).abs());
            }
            return durationOnTheGround.toHours() >= 2;
        });
        return this;
    }
}
