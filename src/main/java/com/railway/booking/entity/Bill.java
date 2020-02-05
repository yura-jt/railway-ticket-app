package com.railway.booking.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Bill {
    private final Integer orderId;
    private final BillStatus billStatus;
    private final BigDecimal price;
    private final User user;
    private final LocalDateTime createdOn;

    private Bill(Builder builder) {
        this.orderId = builder.orderId;
        this.billStatus = builder.billStatus;
        this.price = builder.price;
        this.user = builder.user;
        this.createdOn = builder.createdOn;
    }

    public Integer getOrderId() {
        return orderId;
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
                "orderId=" + orderId +
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
        private Integer orderId;
        private BillStatus billStatus;
        private BigDecimal price;
        private User user;
        private LocalDateTime createdOn;

        private Builder() {
        }

        public Builder withOrderId(Integer orderId) {
            this.orderId = orderId;
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