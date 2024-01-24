package com.eseo.lagence.lagence.services;

import com.eseo.lagence.lagence.models.Properties;
import com.eseo.lagence.lagence.models.Rental;
import com.eseo.lagence.lagence.models.UserAccount;
import com.fasterxml.jackson.databind.JsonNode;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RentalService{

    public static ObservableList<Rental> getTenants() {

        JsonNode rootNode = RequestService.getInstance().sendHttpRequest("/user/rental", RequestService.HttpMethod.GET, Optional.empty() );


        if (rootNode != null && rootNode.isArray()) {
            List<Rental> rentals = new ArrayList<>();

            for (JsonNode userNode : rootNode) {
                JsonNode propertyNode = userNode.get("rentedProperty");

                String id = userNode.get("id").asText();

                String accomodationId = propertyNode.get("id").asText();
                String name = propertyNode.get("name").asText();
                Double price = propertyNode.get("price").asDouble();
                Integer chargesPrice = propertyNode.get("chargesPrice").asInt();
                String description = propertyNode.get("description").asText();
                String address = propertyNode.get("address").asText();
                Integer roomsCount = propertyNode.get("roomsCount").asInt();
                Integer surface = propertyNode.get("surface").asInt();
                String type = propertyNode.get("type").asText();

                String email = userNode.get("email").asText();
                String role = userNode.get("role").asText();
                String firstName = userNode.get("firstName").asText();
                String lastName = userNode.get("lastName").asText();
                Rental rental = new Rental(id,new Properties(accomodationId, name, price, chargesPrice, description, address, roomsCount, surface, type), new UserAccount(id, email, firstName, lastName));
                rentals.add(rental);
            }
            return FXCollections.observableArrayList(rentals);
        } else {
            System.err.println("Unexpected JSON structure. Unable to deserialize accommodations.");
            return FXCollections.observableArrayList();
        }
    }
}
