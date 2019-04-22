package com.zedelivery.pontoDeVenda.domain;

import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

public class Address implements Serializable {

    @NotNull
    @Field("type")
    private String type;

    @NotNull
    @Field("coordinates")
    private Double[] coordinates;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Address type(String type) {
        this.type = type;
        return this;
    }

    public Double[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Double[] coordinates) {
        this.coordinates = coordinates;
    }

    public Address coordinates(Double[] coordinates) {
        this.coordinates = coordinates;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;
        Address address = (Address) o;
        return getType().equals(address.getType()) &&
                Arrays.equals(getCoordinates(), address.getCoordinates());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getType());
        result = 31 * result + Arrays.hashCode(getCoordinates());
        return result;
    }

    @Override
    public String toString() {
        return "Address{" +
                "type='" + type + '\'' +
                ", coordinates=" + Arrays.toString(coordinates) +
                '}';
    }
}
