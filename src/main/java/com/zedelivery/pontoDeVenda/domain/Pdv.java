package com.zedelivery.pontoDeVenda.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Pdv.
 */
@Document(collection = "pdv")
public class Pdv implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("trading_name")
    private String tradingName;

    @NotNull
    @Field("owner_name")
    private String ownerName;

    @NotNull
    @Field("document")
    private String document;

    @NotNull
    @Field("coverageArea")
    private CoverageArea coverageArea;

    @NotNull
    @Field("address")
    private Address address;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTradingName() {
        return tradingName;
    }

    public void setTradingName(String tradingName) {
        this.tradingName = tradingName;
    }

    public Pdv tradingName(String tradingName) {
        this.tradingName = tradingName;
        return this;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Pdv ownerName(String ownerName) {
        this.ownerName = ownerName;
        return this;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public Pdv document(String document) {
        this.document = document;
        return this;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Pdv address(Address address) {
        this.address = address;
        return this;
    }

    public CoverageArea getCoverageArea() {
        return coverageArea;
    }

    public void setCoverageArea(CoverageArea coverageArea) {
        this.coverageArea = coverageArea;
    }


    public Pdv coverageArea(CoverageArea coverageArea) {
        this.coverageArea = coverageArea;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Pdv pdv = (Pdv) o;
        if (pdv.getId() == null || getId() == null) {
            return false;
        }
        return getId().equals(pdv.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Pdv{" +
                "id='" + id + '\'' +
                ", tradingName='" + tradingName + '\'' +
                ", ownerName='" + ownerName + '\'' +
                ", document='" + document + '\'' +
                ", address=" + address +
                ", coverageArea=" + coverageArea +
                '}';
    }
}