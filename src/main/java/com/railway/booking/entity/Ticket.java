package com.railway.booking.entity;

import java.math.BigDecimal;
import java.util.Objects;

public class Ticket {
    private final Integer id;
    private final String departureStation;
    private final String destinationStation;
    private final String passengerName;
    private final BigDecimal price;
    private final Integer flightId;
    private final Integer seatId;
    private final Integer userId;
    private final Integer orderId;

    private Ticket(Builder builder) {
        this.id = builder.id;
        this.departureStation = builder.departureStation;
        this.destinationStation = builder.destinationStation;
        this.passengerName = builder.passengerName;
        this.price = builder.price;
        this.flightId = builder.flightId;
        this.seatId = builder.seatId;
        this.userId = builder.userId;
        this.orderId = builder.orderId;
    }

    public Integer getId() {
        return id;
    }

    public String getDepartureStation() {
        return departureStation;
    }

    public String getDestinationStation() {
        return destinationStation;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getFlightId() {
        return flightId;
    }

    public Integer getSeatId() {
        return seatId;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(id, ticket.id) &&
                Objects.equals(departureStation, ticket.departureStation) &&
                Objects.equals(destinationStation, ticket.destinationStation) &&
                Objects.equals(passengerName, ticket.passengerName) &&
                Objects.equals(price, ticket.price) &&
                Objects.equals(flightId, ticket.flightId) &&
                Objects.equals(seatId, ticket.seatId) &&
                Objects.equals(userId, ticket.userId) &&
                Objects.equals(orderId, ticket.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, departureStation, destinationStation, passengerName, price, flightId, seatId, userId, orderId);
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", departureStation='" + departureStation + '\'' +
                ", destinationStation='" + destinationStation + '\'' +
                ", passengerName='" + passengerName + '\'' +
                ", price=" + price +
                ", flightId=" + flightId +
                ", seatId=" + seatId +
                ", userId=" + userId +
                ", orderId=" + orderId +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Integer id;
        private String departureStation;
        private String destinationStation;
        private String passengerName;
        private BigDecimal price;
        private Integer flightId;
        private Integer seatId;
        private Integer userId;
        private Integer orderId;

        private Builder() {
        }

        public Builder withId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder withDepartureStation(String departureStation) {
            this.departureStation = departureStation;
            return this;
        }

        public Builder withDestinationStation(String destinationStation) {
            this.destinationStation = destinationStation;
            return this;
        }

        public Builder withPassengerName(String passengerName) {
            this.passengerName = passengerName;
            return this;
        }

        public Builder withPrice(BigDecimal price) {
            this.price = price;
            return this;
        }

        public Builder withFlightId(Integer flightId) {
            this.flightId = flightId;
            return this;
        }

        public Builder withSeatId(Integer seatId) {
            this.seatId = seatId;
            return this;
        }

        public Builder withUserId(Integer userId) {
            this.userId = userId;
            return this;
        }

        public Builder withOrderId(Integer orderId) {
            this.seatId = orderId;
            return this;
        }

        public Ticket build() {
            return new Ticket(this);
        }
    }
}