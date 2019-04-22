package com.zedelivery.pontoDeVenda.domain;

import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

public class CoverageArea implements Serializable {

    @NotNull
    @Field("type")
    private String type;

    @NotNull
    @Field("coordinates")
    private Double[][][][] coordinates;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public CoverageArea type(String type) {
        this.type = type;
        return this;
    }

    public Double[][][][] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Double[][][][] coordinates) {
        this.coordinates = coordinates;
    }

    public CoverageArea coordinates(Double[][][][] coordinates) {
        this.coordinates = coordinates;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CoverageArea)) return false;
        CoverageArea that = (CoverageArea) o;
        return getType().equals(that.getType()) &&
                Arrays.equals(getCoordinates(), that.getCoordinates());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getType());
        result = 31 * result + Arrays.hashCode(getCoordinates());
        return result;
    }

    @Override
    public String toString() {
        return "CoverageArea{" +
                "type='" + type + '\'' +
                ", coordinates=" + Arrays.toString(coordinates) +
                '}';
    }
}
