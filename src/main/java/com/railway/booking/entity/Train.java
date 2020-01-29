package com.railway.booking.entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class Train {
    private final Integer id;
    private final String code;
    private final String name;
    private final String departureStation;
    private final String destinationStation;
    private final LocalDateTime departureTime;
    private final LocalDateTime arriveTime;

    private Train(Builder builder) {
        this.id = builder.id;
        this.code = builder.code;
        this.name = builder.name;
        this.departureStation = builder.departureStation;
        this.destinationStation = builder.destinationStation;
        this.departureTime = builder.departureTime;
        this.arriveTime = builder.arriveTime;
    }

    public Integer getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getDepartureStation() {
        return departureStation;
    }

    public String getDestinationStation() {
        return destinationStation;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public LocalDateTime getArriveTime() {
        return arriveTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Train train = (Train) o;
        return Objects.equals(id, train.id) &&
                Objects.equals(code, train.code) &&
                Objects.equals(name, train.name) &&
                Objects.equals(departureStation, train.departureStation) &&
                Objects.equals(destinationStation, train.destinationStation) &&
                Objects.equals(departureTime, train.departureTime) &&
                Objects.equals(arriveTime, train.arriveTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, name, departureStation, destinationStation, departureTime, arriveTime);
    }

    @Override
    public String toString() {
        return "Train{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", departureStation='" + departureStation + '\'' +
                ", destinationStation='" + destinationStation + '\'' +
                ", departureTime=" + departureTime +
                ", arriveTime=" + arriveTime +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Integer id;
        private String code;
        private String name;
        private String departureStation;
        private String destinationStation;
        private LocalDateTime departureTime;
        private LocalDateTime arriveTime;

        private Builder() {
        }

        public Builder withId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder withCode(String code) {
            this.code = code;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
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

        public Builder withDepartureTime(LocalDateTime departureTime) {
            this.departureTime = departureTime;
            return this;
        }

        public Builder withArriveTime(LocalDateTime arriveTime) {
            this.arriveTime = arriveTime;
            return this;
        }

        public Train build() {
            return new Train(this);
        }
    }
}