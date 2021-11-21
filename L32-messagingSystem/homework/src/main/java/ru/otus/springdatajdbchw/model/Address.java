package ru.otus.springdatajdbchw.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Objects;

@Table("address")
public class Address {

    @Id
    private Long id;

    private String street;

    private String zipCode;

    private Long clientId;

    public Address() {
    }

    public Address(String street, String zipCode) {
        this.id = null;
        this.street = street;
        this.zipCode = zipCode;
    }

    public Address(long id, String street, String zipCode, Long clientId) {
        this.id = id;
        this.street = street;
        this.zipCode = zipCode;
        this.clientId = clientId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;
        Address address = (Address) o;
        return id.equals(address.id) &&
                Objects.equals(street, address.street) &&
                Objects.equals(zipCode, address.zipCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, street, zipCode);
    }

    @Override
    public String toString() {
        return "street: " + street + ", zipCode: " + zipCode;
    }
}
