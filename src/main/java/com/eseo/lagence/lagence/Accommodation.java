package com.eseo.lagence.lagence;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Accommodation {

    private Integer id;
    private String name;
    private Double price;
    private String description;
    private String address;
    private Integer roomsCount;
    private Integer surface;
    private String type;
    private Integer chargesPrice;



    public Accommodation(Integer id, String name, Double price, String description, //
                         String address, Integer roomsCount, Integer surface) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.address = address;
        this.roomsCount = roomsCount;
        this.surface = surface;
    }





    public static Accommodation fromJson(String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, Accommodation.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getRoomsCount() {
        return roomsCount;
    }

    public void setRoomsCount(Integer roomsCount) {
        this.roomsCount = roomsCount;
    }

    public Integer getSurface() {
        return surface;
    }

    public void setSurface(Integer surface) {
        this.surface = surface;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getChargesPrice() {
        return chargesPrice;
    }

    public void setChargesPrice(Integer chargesPrice) {
        this.chargesPrice = chargesPrice;
    }
}
