package com.railway.booking.entity;

import com.railway.booking.entity.enums.OrderStatus;

public class Order {
    private final Integer id;
    private final OrderStatus status;
    private final Integer userId;
    private final Integer seatId;

    private Order(Builder builder) {
        this.id = builder.id;
        this.status = builder.status;
        this.userId = builder.userId;
        this.seatId = builder.seatId;
    }

    public Integer getId() {
        return id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getSeatId() {
        return seatId;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", status=" + status +
                ", userId=" + userId +
                ", seatId=" + seatId +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Integer id;
        private OrderStatus status;
        private Integer userId;
        private Integer seatId;

        private Builder() {
        }

        public Builder withId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder withSeatStatus(OrderStatus status) {
            this.status = status;
            return this;
        }

        public Builder withUserId(Integer userId) {
            this.userId = userId;
            return this;
        }

        public Builder withSeatId(Integer seatId) {
            this.seatId = seatId;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }
}