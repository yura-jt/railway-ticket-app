package com.railway.booking.entity;

public class Seat {
    private final Integer billId;
    private final Integer number;
    private final Integer carriageId;
    private final SeatStatus status;

    private Seat(Builder builder) {
        this.billId = builder.billId;
        this.number = builder.number;
        this.carriageId = builder.carriageId;
        this.status = builder.status;
    }

    public Integer getBillId() {
        return billId;
    }

    public Integer getNumber() {
        return number;
    }

    public SeatStatus getStatus() {
        return status;
    }

    public Integer getCarriageId() {
        return carriageId;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Integer billId;
        private Integer number;
        private Integer carriageId;
        private SeatStatus status;

        private Builder() {
        }

        public Builder withBillId(Integer billId) {
            this.billId = billId;
            return this;
        }

        public Builder withNumber(Integer number) {
            this.number = number;
            return this;
        }

        public Builder withCarriageId(Integer carriageId) {
            this.carriageId = carriageId;
            return this;
        }

        public Builder withSeatStatus(SeatStatus status) {
            this.status = status;
            return this;
        }

        public Seat build() {
            return new Seat(this);
        }
    }
}