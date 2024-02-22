package com.gridnine.testing;

import com.gridnine.testing.builder.FlightBuilder;
import com.gridnine.testing.model.Flight;
import com.gridnine.testing.service.iml.FlightFilteringImpl;

import java.util.List;

public class Main {
    public static void printResultList(String text, List<Flight> flights) {
        System.out.println(text + ":");
        flights.forEach(System.out::println);
    }
    public static void main(String[] args) {
        List<Flight> flightList = FlightBuilder.createFlights();
        String withoutFilter = "Список рейсов без фильтров";
        printResultList(withoutFilter, flightList);

        List<Flight> departureBeforeNowFilter = new FlightFilteringImpl(flightList)
                .departureBeforeNow()
                .build();
        String withoutDepartureBeforeNow = "Список рейсов без учета рейсов c вылетом до текущего момента времени";
        printResultList(withoutDepartureBeforeNow, departureBeforeNowFilter);

        List<Flight> arrivalDateBeforeDepartureDateFilter = new FlightFilteringImpl(flightList)
                .segmentsWithArrivalDateBeforeDepartureDate()
                .build();
        String withoutArrivalBeforeDeparture = "Список рейсов без учета рейсов с датой прилёта раньше даты вылета";
        printResultList(withoutArrivalBeforeDeparture, arrivalDateBeforeDepartureDateFilter);

        List<Flight> totalTimeOnTheGroundExceedsTwoHoursFilter = new FlightFilteringImpl(flightList)
                .flightsWhereTotalTimeOnTheGroundExceedsTwoHours()
                .build();
        String withoutTimeOnTheGroundExceedsTwoHours = "Перелеты, где общее время, проведённое на земле, превышает два часа";
        printResultList(withoutTimeOnTheGroundExceedsTwoHours, totalTimeOnTheGroundExceedsTwoHoursFilter);
    }
}