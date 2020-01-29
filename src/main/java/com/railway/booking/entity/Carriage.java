package com.railway.booking.entity;

import com.railway.booking.entity.enums.CarriageType;

public class Carriage {
    private final Integer id;
    private final CarriageType type;
    private final Integer number;
    private final Integer flightId;

    private Carriage(Builder builder) {
        this.id = builder.id;
        this.type = builder.type;
        this.number = builder.number;
        this.flightId = builder.flightId;
    }

    public Integer getId() {
        return id;
    }

    public CarriageType getType() {
        return type;
    }

    public Integer getNumber() {
        return number;
    }

    public Integer getFlightId() {
        return flightId;
    }

    @Override
    public String toString() {
        return "Carriage{" +
                "id=" + id +
                ", type=" + type +
                ", number=" + number +
                ", flight_id=" + flightId +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Integer id;
        private CarriageType type;
        private Integer number;
        private Integer flightId;

        private Builder() {
        }

        public Builder withId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder withCarriageType(CarriageType type) {
            this.type = type;
            return this;
        }

        public Builder withNumber(Integer number) {
            this.number = number;
            return this;
        }

        public Builder withFlightId(Integer flightId) {
            this.flightId = flightId;
            return this;
        }

        public Carriage build() {
            return new Carriage(this);
        }
    }
}