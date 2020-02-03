package com.railway.booking.entity;

import com.railway.booking.entity.enums.StationType;

import java.time.LocalTime;
import java.util.Objects;

public class Station {
    private final Integer id;
    private final String name;
    private final StationType stationType;
    private final LocalTime time;
    private final Integer distance;
    private final Integer trainId;

    private Station(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.stationType = builder.stationType;
        this.time = builder.time;
        this.distance = builder.distance;
        this.trainId = builder.trainId;
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

    public LocalTime getTime() {
        return time;
    }

    public Integer getDistance() {
        return distance;
    }

    public Integer getTrainId() {
        return trainId;
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
                Objects.equals(trainId, station.trainId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, stationType, time, distance, trainId);
    }

    @Override
    public String toString() {
        return "Station{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", stationType=" + stationType +
                ", time=" + time.toString() +
                ", distance=" + distance +
                ", trainId=" + trainId +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Integer id;
        private String name;
        private StationType stationType;
        private LocalTime time;
        private Integer distance;
        private Integer trainId;

        private Builder() {
        }

        public Builder withId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withStationType(StationType stationType) {
            this.stationType = stationType;
            return this;
        }

        public Builder withTime(LocalTime time) {
            this.time = time;
            return this;
        }

        public Builder withDistance(Integer distance) {
            this.distance = distance;
            return this;
        }

        public Builder withTrainId(Integer trainId) {
            this.trainId = trainId;
            return this;
        }

        public Station build() {
            return new Station(this);
        }
    }
}