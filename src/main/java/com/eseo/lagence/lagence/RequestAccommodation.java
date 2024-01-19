package com.eseo.lagence.lagence;

public class RequestAccommodation {

    private String id;
    private final UserAccount user;
    private final Accommodation accommodation;

    public RequestAccommodation(String id, UserAccount user, Accommodation accommodation) {
        this.id = id;
        this.user = user;
        this.accommodation = accommodation;
    }

    public RequestAccommodation(String id, String userId, String userEmail, String userFirstName, String userLastName,
                                String accommodationId, String accommodationName, double accommodationPrice, String accommodationDescription, String accommodationLocation,
                                int accommodationRooms, int accommodationSize) {
        this.id = id;
        this.user = new UserAccount(userId, userEmail, userFirstName, userLastName);
        this.accommodation = new Accommodation(accommodationId, accommodationName, accommodationPrice, accommodationDescription, accommodationLocation,
                accommodationRooms, accommodationSize);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public UserAccount getUser() {
        return user;
    }

    public Accommodation getAccommodation() {
        return accommodation;
    }

    public String getLocation() {
        return accommodation.getAddress();
    }


}
