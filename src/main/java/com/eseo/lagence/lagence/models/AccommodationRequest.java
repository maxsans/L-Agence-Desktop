package com.eseo.lagence.lagence.models;

public class AccommodationRequest {

    private String id;

    private String motivationText;
    private String idCardPath;
    private String proofOfAddressPath;
    private String state;
    private final UserAccount user;
    private final Properties accommodation;

    public AccommodationRequest(String id, String motivationText, String idCardPath, String proofOfAddressPath, String state, UserAccount user, Properties accommodation) {
        this.id = id;
        this.motivationText= motivationText;
        this.idCardPath = idCardPath;
        this.proofOfAddressPath = proofOfAddressPath;
        this.state = state;
        this.user = user;
        this.accommodation = accommodation;
    }

    public AccommodationRequest(String id, String userId, String userEmail, String userFirstName, String userLastName,
                                String accommodationId, String accommodationName, double accommodationPrice, Integer chargesPrice, String accommodationDescription, String accommodationLocation,
                                int accommodationRooms, int accommodationSize) {
        this.id = id;
        this.user = new UserAccount(userId, userEmail, userFirstName, userLastName);
        this.accommodation = new Properties(accommodationId, accommodationName, accommodationPrice, chargesPrice, accommodationDescription, accommodationLocation,
                accommodationRooms, accommodationSize, "apartment");
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

    public Properties getAccommodation() {
        return accommodation;
    }

    public String getLocation() {
        return accommodation.getAddress();
    }

    public String getMotivationText() {
        return motivationText;
    }

    public void setMotivationText(String motivationText) {
        this.motivationText = motivationText;
    }

    public String getIdCardPath() {
        return idCardPath;
    }

    public void setIdCardPath(String idCardPath) {
        this.idCardPath = idCardPath;
    }

    public String getProofOfAddressPath() {
        return proofOfAddressPath;
    }

    public void setProofOfAddressPath(String proofOfAddressPath) {
        this.proofOfAddressPath = proofOfAddressPath;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
