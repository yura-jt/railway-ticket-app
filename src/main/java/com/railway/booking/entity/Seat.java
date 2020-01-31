package com.railway.booking.entity;

import com.railway.booking.entity.enums.SeatStatus;

public class Seat {
    private final Integer id;
    private final Integer number;
    private final Integer carriageId;
    private final Integer ticketId;
    private final SeatStatus status;

    private Seat(Builder builder) {
        this.id = builder.id;
        this.number = builder.number;
        this.carriageId = builder.carriageId;
        this.ticketId = builder.ticketId;
        this.status = builder.status;
    }

    public Integer getId() {
        return id;
    }

    public Integer getCarriageId() {
        return carriageId;
    }

    public Integer getTicketId() {
        return ticketId;
    }

    public SeatStatus getStatus() {
        return status;
    }

    public Integer getNumber() {
        return number;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Integer id;
        private Integer number;
        private Integer carriageId;
        private Integer ticketId;
        private SeatStatus status;

        private Builder() {
        }

        public Builder withId(Integer id) {
            this.id = id;
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

        public Builder withTicketId(Integer ticketId) {
            this.ticketId = ticketId;
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