package com.railway.booking.entity;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Train {
    private final Integer id;
    private final String code;
    private final String name;
    private final List<Station> stations;

    private Train(Builder builder) {
        this.id = builder.id;
        this.code = builder.code;
        this.name = builder.name;
        this.stations = builder.stations;
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

    public List<Station> getStations() {
        return stations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Train train = (Train) o;
        return Objects.equals(id, train.id) &&
                Objects.equals(code, train.code) &&
                Objects.equals(name, train.name) &&
                Objects.equals(stations, train.stations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, name, stations);
    }

    @Override
    public String toString() {
        return "Train{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", stations=" + stations.stream()
                .map(Objects::toString)
                .collect(Collectors.joining(",\n")) +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Integer id;
        private String code;
        private String name;
        private List<Station> stations;

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

        public Builder withStations(List<Station> stations) {
            this.stations = stations;
            return this;
        }

        public Train build() {
            return new Train(this);
        }
    }
}