package com.eseo.lagence.lagence;

public class Rental{
    private String id;
    private Accommodation accommodation;
    private UserAccount user;

    public Rental(String  id, Accommodation accommodation, UserAccount user) {
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

    public Accommodation getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(Accommodation accommodation) {
        this.accommodation = accommodation;
    }

    public UserAccount getUser() {
        return user;
    }

    public void setUser(UserAccount user) {
        this.user = user;
    }

}
