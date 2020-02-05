package com.railway.booking.entity;

import java.util.Objects;

public class Carriage {
    private final Integer id;
    private final CarriageType type;
    private final Integer number;
    private final Integer capacity;
    private final Integer flightId;

    private Carriage(Builder builder) {
        this.id = builder.id;
        this.type = builder.type;
        this.number = builder.number;
        this.flightId = builder.flightId;
        this.capacity = builder.capacity;
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

    public Integer getCapacity() {
        return capacity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Carriage carriage = (Carriage) o;
        return Objects.equals(id, carriage.id) &&
                type == carriage.type &&
                Objects.equals(number, carriage.number) &&
                Objects.equals(capacity, carriage.capacity) &&
                Objects.equals(flightId, carriage.flightId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, number, capacity, flightId);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Integer id;
        private CarriageType type;
        private Integer number;
        private Integer flightId;
        private Integer capacity;

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

        public Builder withCapacity(Integer capacity) {
            this.capacity = capacity;
            return this;
        }

        public Carriage build() {
            return new Carriage(this);
        }
    }
}