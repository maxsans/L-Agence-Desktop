package com.eseo.lagence.lagence.models;

public class Rental{
    private String id;
    private Properties accommodation;
    private UserAccount user;

    public Rental(String  id, Properties accommodation, UserAccount user) {
        this.id = id;
        this.accommodation = accommodation;
        this.user = user;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Properties getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(Properties accommodation) {
        this.accommodation = accommodation;
    }

    public UserAccount getUser() {
        return user;
    }

    public void setUser(UserAccount user) {
        this.user = user;
    }

}
