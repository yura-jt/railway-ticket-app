package com.railway.booking.entity;

import com.railway.booking.entity.enums.StationType;

import java.time.LocalDateTime;
import java.util.Objects;

public class Station {
    private final Integer id;
    private final String name;
    private final StationType stationType;
    private final LocalDateTime time;
    private final Integer distance;
    private final Train train;

    private Station(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.stationType = builder.stationType;
        this.time = builder.time;
        this.distance = builder.distance;
        this.train = builder.train;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public StationType getStationType() {
        return stationType;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public Integer getDistance() {
        return distance;
    }

    public Train getTrain() {
        return train;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Station station = (Station) o;
        return Objects.equals(id, station.id) &&
                Objects.equals(name, station.name) &&
                stationType == station.stationType &&
                Objects.equals(time, station.time) &&
                Objects.equals(distance, station.distance) &&
                Objects.equals(train, station.train);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, stationType, time, distance, train);
    }

    @Override
    public String toString() {
        return "Station{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", stationType=" + stationType +
                ", time=" + time +
                ", distance=" + distance +
                ", train=" + train +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Integer id;
        private String name;
        private StationType stationType;
        private LocalDateTime time;
        private Integer distance;
        private Train train;

        private Builder() {
        }

        public Builder withStationType(StationType stationType) {
            this.stationType = stationType;
            return this;
        }

        public Builder withTime(LocalDateTime time) {
            this.time = time;
            return this;
        }

        public Builder withDistance(Integer distance) {
            this.distance = distance;
            return this;
        }

        public Builder withTrain(Train train) {
            this.train = train;
            return this;
        }

        public Station build() {
            return new Station(this);
        }
    }
}