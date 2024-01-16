package com.eseo.lagence.lagence;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;


public class RequestService {
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public RequestService() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }
    public ObservableList<Accommodation> getAccommodations() {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:3000/api/property"))
                .GET()
                .build();

        try {
            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            System.out.println("Status Code: " + httpResponse.statusCode());
            System.out.println("Response Body:\n" + httpResponse.body());
            return deserializeAccommodations(httpResponse.body());
        } catch (Exception e) {
            e.printStackTrace();
            return FXCollections.observableArrayList();
        }
    }

    private ObservableList<Accommodation> deserializeAccommodations(String json) throws JsonProcessingException {
        JsonNode rootNode = objectMapper.readTree(json);
        JsonNode propertiesNode = rootNode.get("properties");

        if (propertiesNode != null && propertiesNode.isArray()) {
            List<Accommodation> accommodations = new ArrayList<>();

            for (JsonNode accommodationNode : propertiesNode) {
                Integer id = accommodationNode.get("id").asInt();
                String name = accommodationNode.get("name").asText();
                Double price = accommodationNode.get("price").asDouble();
                String description = accommodationNode.get("description").asText();
                String address = accommodationNode.get("address").asText();
                Integer roomsCount = accommodationNode.get("roomsCount").asInt();
                Integer surface = accommodationNode.get("surface").asInt();

                Accommodation accommodation = new Accommodation(id, name, price, description, address, roomsCount, surface);
                accommodations.add(accommodation);
            }

            return FXCollections.observableArrayList(accommodations);
        } else {
            // Gérer le cas où "properties" n'est pas présent ou n'est pas un tableau
            System.err.println("Unexpected JSON structure. Unable to deserialize accommodations.");
            return FXCollections.observableArrayList();
        }
    }


}
