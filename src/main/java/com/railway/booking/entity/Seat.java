package com.railway.booking.entity;

import com.railway.booking.entity.enums.SeatStatus;

public class Seat {
    private final Integer id;
    private final Integer carriageId;
    private final Integer ticketId;
    private final SeatStatus status;

    private Seat(Builder builder) {
        this.id = builder.id;
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

    @Override
    public String toString() {
        return "Seat{" +
                "id=" + id +
                ", carriageId=" + carriageId +
                ", ticketId=" + ticketId +
                ", status=" + status +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Integer id;
        private Integer carriageId;
        private Integer ticketId;
        private SeatStatus status;

        private Builder() {
        }

        public Builder withId(Integer id) {
            this.id = id;
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