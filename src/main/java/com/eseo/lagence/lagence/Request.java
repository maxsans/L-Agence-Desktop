package com.eseo.lagence.lagence;

public class Request {

    private Integer id;
    private final UserAccount user;
    private final Accommodation accommodation;

    public Request(int id, UserAccount user, Accommodation accommodation) {
        this.id = id;
        this.user = user;
        this.accommodation = accommodation;
    }

    public Request(int id, int userId, String userEmail, String userFirstName, String userLastName,
                   int accommodationId, String accommodationName, double accommodationPrice, String accommodationDescription, String accommodationLocation,
                   int accommodationRooms, int accommodationSize) {
        this.id = id;
        this.user = new UserAccount(userId, userEmail, userFirstName, userLastName);
        this.accommodation = new Accommodation(accommodationId, accommodationName, accommodationPrice, accommodationDescription, accommodationLocation,
                accommodationRooms, accommodationSize);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public UserAccount getUser() {
        return user;
    }

    public Accommodation getAccommodation() {
        return accommodation;
    }

    public String getLocation() {
        return accommodation.getLocation();
    }


}
