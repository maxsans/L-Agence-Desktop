package com.eseo.lagence.lagence;

import com.eseo.lagence.lagence.UserAccount;

public class Accommodation {

    private int id;
    private String name;
    private double price;
    private String description;
    private String location;
    private int numberOfRooms;
    private int size;
    private boolean isRented;
    private UserAccount rentedUser;


    public Accommodation(int id, String name, double price, String description, //
                         String location, int numberOfRooms, int size, boolean isRented, UserAccount rentedUser) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.location = location;
        this.numberOfRooms = numberOfRooms;
        this.size = size;
        this.isRented = isRented;
        this.rentedUser = rentedUser;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
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

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isRented() {
        return isRented;
    }

    public void setRented(boolean rented) {
        isRented = rented;
    }

    public UserAccount getRentedUser() {
        return rentedUser;
    }

    public void setRentedUser(UserAccount rentedUser) {
        this.rentedUser = rentedUser;
    }

    public void setId(int id) {
        this.id = id;
    }

}
