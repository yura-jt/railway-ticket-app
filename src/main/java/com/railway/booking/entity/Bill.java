package com.railway.booking.entity;

import com.railway.booking.entity.enums.BillStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Bill {
    private final Integer id;
    private final Order order;
    private final BillStatus billStatus;
    private final BigDecimal price;
    private final User user;
    private final LocalDateTime createdOn;

    private Bill(Builder builder) {
        this.id = builder.id;
        this.order = builder.order;
        this.billStatus = builder.billStatus;
        this.price = builder.price;
        this.user = builder.user;
        this.createdOn = builder.createdOn;
    }

    public Integer getId() {
        return id;
    }

    public Order getOrder() {
        return order;
    }

    public BillStatus getStatus() {
        return billStatus;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public User getUser() {
        return user;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "id=" + id +
                ", order=" + order +
                ", status=" + billStatus +
                ", price=" + price +
                ", user=" + user +
                ", createdOn=" + createdOn +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Integer id;
        private Order order;
        private BillStatus billStatus;
        private BigDecimal price;
        private User user;
        private LocalDateTime createdOn;

        private Builder() {
        }

        public Builder withId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder withOrder(Order order) {
            this.order = order;
            return this;
        }

        public Builder withBillStatus(BillStatus billStatus) {
            this.billStatus = billStatus;
            return this;
        }

        public Builder withPrice(BigDecimal price) {
            this.price = price;
            return this;
        }

        public Builder withUser(User user) {
            this.user = user;
            return this;
        }

        public Builder withCreatedOn(LocalDateTime createdOn) {
            this.createdOn = createdOn;
            return this;
        }

        public Bill build() {
            return new Bill(this);
        }
    }
}