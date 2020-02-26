package com.railway.booking.dto;

import com.railway.booking.entity.Flight;

public class FlightDto {
    private final String codeTrain;
    private final String date;
    private final String name;
    private final int freeSeatsAmount;
    private final Flight flight;

    public FlightDto(String codeTrain, String date, String name, int freeSeatsAmount, Flight flight) {
        this.codeTrain = codeTrain;
        this.name = name;
        this.date = date;
        this.freeSeatsAmount = freeSeatsAmount;
        this.flight = flight;
    }

    public String getCodeTrain() {
        return codeTrain;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public int getFreeSeatsAmount() {
        return freeSeatsAmount;
    }

    public Flight getFlight() {
        return flight;
    }
}