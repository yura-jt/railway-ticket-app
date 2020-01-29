package com.railway.booking.entity;

public class Flight {
    private final Integer id;
    private final String departureDate;
    private final Integer trainId;

    public Flight(Integer id, String departureDate, Integer trainId) {
        this.id = id;
        this.departureDate = departureDate;
        this.trainId = trainId;
    }

    public Integer getId() {
        return id;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public Integer getTrainId() {
        return trainId;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "id=" + id +
                ", departureDate='" + departureDate + '\'' +
                ", trainId=" + trainId +
                '}';
    }
}
