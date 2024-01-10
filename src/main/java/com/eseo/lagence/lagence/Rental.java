package com.eseo.lagence.lagence;

public class Rental{
    private int id;
    private Accommodation accommodation;
    private UserAccount user;

    public Rental(int id, Accommodation accommodation, UserAccount user) {
        this.id = id;
        this.accommodation = accommodation;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
