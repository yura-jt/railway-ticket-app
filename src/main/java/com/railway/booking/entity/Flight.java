package com.railway.booking.entity;

import java.time.LocalDateTime;

public class Flight {
    private final Integer id;
    private final LocalDateTime departureDate;
    private final Integer trainId;

    public Flight(Integer id, LocalDateTime departureDate, Integer trainId) {
        this.id = id;
        this.departureDate = departureDate;
        this.trainId = trainId;
    }

    public Integer getId() {
        return id;
    }

    public LocalDateTime getDepartureDate() {
        return departureDate;
    }

    public Integer getTrainId() {
        return trainId;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "id=" + id +
                ", departureDate='" + departureDate.toString() + '\'' +
                ", trainId=" + trainId +
                '}';
    }
}