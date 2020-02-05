package com.railway.booking.entity;

import com.railway.booking.entity.enums.CarriageType;

import java.math.BigDecimal;
import java.util.Objects;

public class Tariff {
    private final Integer id;
    private final CarriageType carriageType;
    private final BigDecimal rate;

    public Tariff(Integer id, CarriageType carriageType, BigDecimal rate) {
        this.id = id;
        this.carriageType = carriageType;
        this.rate = rate;
    }

    public Integer getId() {
        return id;
    }

    public CarriageType getCarriageType() {
        return carriageType;
    }

    public BigDecimal getRate() {
        return rate;
    }

    @Override
    public String toString() {
        return "Tariff{" +
                "id=" + id +
                ", carriageType=" + carriageType +
                ", rate=" + rate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tariff tariff = (Tariff) o;
        return Objects.equals(id, tariff.id) &&
                carriageType == tariff.carriageType &&
                Objects.equals(rate, tariff.rate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, carriageType, rate);
    }
}