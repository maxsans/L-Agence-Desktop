package com.eseo.lagence.lagence;

import com.eseo.lagence.lagence.UserAccount;

public class Accommodation {

    private Integer id;
    private String name;
    private Double price;
    private String description;
    private String location;
    private Integer numberOfRooms;
    private Integer size;
    private boolean isRented;

    public Accommodation(Integer id, String name, Double price, String description, //
                         String location, Integer numberOfRooms, Integer size, boolean isRented) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.location = location;
        this.numberOfRooms = numberOfRooms;
        this.size = size;
        this.isRented = isRented;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(Integer numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public boolean isRented() {
        return isRented;
    }

    public void setRented(boolean rented) {
        isRented = rented;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
