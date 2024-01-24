package com.eseo.lagence.lagence.services;

import com.eseo.lagence.lagence.models.AccommodationRequest;
import com.eseo.lagence.lagence.models.Properties;
import com.eseo.lagence.lagence.models.UserAccount;
import com.fasterxml.jackson.databind.JsonNode;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AccommodationRequestService{

    public static ObservableList<AccommodationRequest> getApply() {

        JsonNode rootNode = RequestService.getInstance().sendHttpRequest("/properties/apply", RequestService.HttpMethod.POST, Optional.empty());
        System.out.println(rootNode);
        List<AccommodationRequest> requestsAccommodation = new ArrayList<>();

        for (JsonNode jsonNode : rootNode) {
            JsonNode propertyNode = jsonNode.get("property");
            JsonNode userNode = jsonNode.get("user");

            if (propertyNode != null && userNode != null) {
                String id = jsonNode.get("id").asText();
                String motivationText = jsonNode.get("motivationText").asText();
                String idCardPath = jsonNode.get("idCardPath").asText();
                String proofOfAddressPath = jsonNode.get("proofOfAddressPath").asText();
                String state = jsonNode.get("state").asText();

                String accomodationId = propertyNode.get("id").asText();
                String name = propertyNode.get("name").asText();
                Double price = propertyNode.get("price").asDouble();
                String description = propertyNode.get("description").asText();
                String address = propertyNode.get("address").asText();
                Integer roomsCount = propertyNode.get("roomsCount").asInt();
                Integer surface = propertyNode.get("surface").asInt();
                Integer chargesPrice = propertyNode.get("chargesPrice").asInt();
                String type = propertyNode.get("type").asText();


                String userId = userNode.get("id").asText();
                String email = userNode.get("email").asText();
                String role = userNode.get("role").asText();
                String firstName = userNode.get("firstName").asText();
                String lastName = userNode.get("lastName").asText();

                Properties accommodation = new Properties(id, name, price, chargesPrice, description, address, roomsCount, surface, type);
                UserAccount userAccount = new UserAccount(userId, email, role, firstName, lastName);
                System.out.println(state);

                if (state.equals("pending")){
                    requestsAccommodation.add(new AccommodationRequest(id, motivationText, idCardPath, proofOfAddressPath, state, userAccount, accommodation));
                }
            } else {
                System.err.println("Unexpected JSON structure. Unable to deserialize apply.");
                return FXCollections.observableArrayList();
            }

        }
        return FXCollections.observableArrayList(requestsAccommodation);
    }
}
