package com.railway.booking.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Order implements Serializable {
    private final Integer id;
    private final String departureStation;
    private final String destinationStation;
    private final LocalDateTime departureDate;
    private final LocalTime fromTime;
    private final LocalTime toTime;
    private final OrderStatus status;
    private final Integer userId;

    private Order(Builder builder) {
        this.id = builder.id;
        this.departureStation = builder.departureStation;
        this.destinationStation = builder.destinationStation;
        this.departureDate = builder.departureDate;
        this.fromTime = builder.fromTime;
        this.toTime = builder.toTime;
        this.status = builder.status;
        this.userId = builder.userId;
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

    public LocalDateTime getDepartureDate() {
        return departureDate;
    }

    public LocalTime getFromTime() {
        return fromTime;
    }

    public LocalTime getToTime() {
        return toTime;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public Integer getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", departureStation='" + departureStation + '\'' +
                ", destinationStation='" + destinationStation + '\'' +
                ", departureDate=" + departureDate +
                ", fromTime=" + fromTime +
                ", toTime=" + toTime +
                ", status=" + status +
                ", userId=" + userId +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Integer id;
        private String departureStation;
        private String destinationStation;
        private LocalDateTime departureDate;
        private LocalTime fromTime;
        private LocalTime toTime;
        private OrderStatus status;
        private Integer userId;

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

        public Builder withDepartureDate(LocalDateTime departureDate) {
            this.departureDate = departureDate;
            return this;
        }

        public Builder withFromTime(LocalTime fromTime) {
            this.fromTime = fromTime;
            return this;
        }

        public Builder withToTime(LocalTime toTime) {
            this.toTime = toTime;
            return this;
        }

        public Builder withOrderStatus(OrderStatus status) {
            this.status = status;
            return this;
        }

        public Builder withUserId(Integer userId) {
            this.userId = userId;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }
}