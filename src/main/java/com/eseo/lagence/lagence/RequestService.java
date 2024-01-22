package com.eseo.lagence.lagence;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public class RequestService {
    private static RequestService instance;

    private String cookieString;

    private UserAccount userLogged;


    private String baseUrl = "http://localhost:3000/api";

    public enum HttpMethod {
        GET,
        POST,
        PUT,
        DELETE
    }

    public static RequestService getInstance() {
        if (RequestService.instance == null) {
            RequestService.instance = new RequestService();
        }
        return RequestService.instance;
    }

    public UserAccount getUserLogged() {
        return userLogged;
    }

    public boolean login(String loginEndpoint, String username, String password) {
        String endpoint = baseUrl + loginEndpoint;

        HttpClient client = HttpClient.newHttpClient();

        // Create a login request
        HttpRequest loginRequest = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("{\"email\":\"" + username + "\", \"password\":\"" + password + "\"}"))
                .build();

        try {
            // Send the login request
            HttpResponse<String> loginResponse = client.send(loginRequest, HttpResponse.BodyHandlers.ofString());
            System.out.println("Response Body:\n" + loginResponse.body());

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(loginResponse.body());
            JsonNode userNode = rootNode.get("user");

            if (userNode != null) {
                String id = userNode.get("id").asText();
                String email = userNode.get("email").asText();
                String role = userNode.get("role").asText();
                String firstName = userNode.get("firstName").asText();
                String lastName = userNode.get("lastName").asText();
                this.userLogged = new UserAccount(id, email, role, firstName, lastName);
            } else {
                System.err.println("Unexpected JSON structure. Unable to deserialize users.");
                return false;
            }

            Map<String, List<String>> headers = loginResponse.headers().map();
            List<String> cookies = headers.get("Set-Cookie");
            String cookieString = String.join("; ", cookies);
            this.cookieString = cookieString;
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            this.userLogged = null;
            this.cookieString = null;
            return false;
        }
    }

    public void logout(String logoutEndpoint) {
        String endpoint = baseUrl + logoutEndpoint;

        HttpClient client = HttpClient.newHttpClient();

        // Create a login request
        HttpRequest loginRequest = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .POST(HttpRequest.BodyPublishers.ofString(""))
                .build();

        try {
            // Send the login request
            HttpResponse<String> logoutResponse = client.send(loginRequest, HttpResponse.BodyHandlers.ofString());
            this.userLogged = null;
            this.cookieString = null;
            return;

        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    public JsonNode sendHttpRequest(String endpoint, HttpMethod httpMethod, Optional<String> requestBody) {
        HttpClient httpClient = HttpClient.newHttpClient();
        ObjectMapper objectMapper = new ObjectMapper();
        String fullEndpoint = baseUrl + endpoint;
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(fullEndpoint));

        if (this.cookieString != null) {
            requestBuilder = requestBuilder.header("Cookie", this.cookieString);
        }

        HttpRequest.BodyPublisher body;
        if (requestBody.isPresent()) {
            body = HttpRequest.BodyPublishers.ofString(requestBody.get());
            requestBuilder = requestBuilder.header("Content-Type", "application/json");
        } else {
            body = HttpRequest.BodyPublishers.noBody();
        }

        switch (httpMethod) {
            case GET:
                requestBuilder = requestBuilder.GET();
                break;
            case POST:
                requestBuilder = requestBuilder.POST(body);
                break;
            case PUT:
                requestBuilder = requestBuilder.PUT(body);
                break;
            case DELETE:
                requestBuilder = requestBuilder.DELETE();
                break;
        }

        HttpRequest httpRequest = requestBuilder.build();

        try {
            HttpResponse<String> httpResponseAccommodation = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            System.out.println("Status Code: " + httpResponseAccommodation.statusCode());
            System.out.println("Response Body:\n" + httpResponseAccommodation.body());
            return objectMapper.readTree(httpResponseAccommodation.body());
        } catch (Exception e) {
            e.printStackTrace();
            return objectMapper.createObjectNode();
        }
    }

    public ObservableList<Accommodation> getAccommodations() {

        JsonNode rootNode = sendHttpRequest("/properties", HttpMethod.GET, Optional.empty());

        JsonNode propertiesNode = rootNode.get("properties");

        if (propertiesNode != null && propertiesNode.isArray()) {
            List<Accommodation> accommodations = new ArrayList<>();

            for (JsonNode accommodationNode : propertiesNode) {
                String id = accommodationNode.get("id").asText();
                String name = accommodationNode.get("name").asText();
                Double price = accommodationNode.get("price").asDouble();
                Integer chargesPrice = accommodationNode.get("chargesPrice").asInt();
                String description = accommodationNode.get("description").asText();
                String address = accommodationNode.get("address").asText();
                Integer roomsCount = accommodationNode.get("roomsCount").asInt();
                Integer surface = accommodationNode.get("surface").asInt();
                String type = accommodationNode.get("type").asText();

                Accommodation accommodation = new Accommodation(id, name, price, chargesPrice, description, address, roomsCount, surface, type);
                accommodations.add(accommodation);
            }

            return FXCollections.observableArrayList(accommodations);
        } else {
            System.err.println("Unexpected JSON structure. Unable to deserialize accommodations.");
            return FXCollections.observableArrayList();
        }
    }

    public ObservableList<UserAccount> getUsers() {
        JsonNode rootNode = sendHttpRequest("/user", HttpMethod.GET, Optional.empty());
        System.out.println(rootNode);

        if (rootNode != null && rootNode.isArray()) {
            List<UserAccount> userAccounts = new ArrayList<>();

            for (JsonNode userNode : rootNode) {
                String id = userNode.get("id").asText();
                String email = userNode.get("email").asText();
                String role = userNode.get("role").asText();
                String firstName = userNode.get("firstName").asText();
                String lastName = userNode.get("lastName").asText();

                UserAccount userAccount = new UserAccount(id, email, role, firstName, lastName);
                userAccounts.add(userAccount);
            }
            return FXCollections.observableArrayList(userAccounts);
        } else {
            System.err.println("Unexpected JSON structure. Unable to deserialize users.");
            return FXCollections.observableArrayList();
        }
    }

    public boolean updateProperty(Accommodation accommodation) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Convert Accommodation object to JSON string
            ObjectNode accommodationJsonNode = objectMapper.createObjectNode();
            accommodationJsonNode.put("id", accommodation.getId());
            accommodationJsonNode.put("name", accommodation.getName());
            accommodationJsonNode.put("price", accommodation.getPrice());
            accommodationJsonNode.put("chargesPrice", accommodation.getChargesPrice());
            accommodationJsonNode.put("description", accommodation.getDescription());
            accommodationJsonNode.put("address", accommodation.getAddress());
            accommodationJsonNode.put("roomsCount", accommodation.getRoomsCount());
            accommodationJsonNode.put("surface", accommodation.getSurface());
            accommodationJsonNode.put("type", accommodation.getType());

            // Convert JSON object to JSON string
            String accommodationJson = objectMapper.writeValueAsString(accommodationJsonNode);

            System.out.println(accommodationJson);

            // Send PUT request with JSON body
            JsonNode rootNode = sendHttpRequest("/properties/" + accommodation.getId(), HttpMethod.PUT, Optional.of(accommodationJson));

            // You can handle the response as needed
            System.out.println("Response from updateProperty: " + rootNode);

            return true;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ObservableList<UserAccount> getApply() {

        JsonNode rootNode = sendHttpRequest("/properties/apply", HttpMethod.GET, Optional.empty());
        System.out.println(rootNode);
/*
        if (rootNode != null && rootNode.isArray()) {
            List<UserAccount> userAccounts = new ArrayList<>();

            for (JsonNode userNode : rootNode) {
                String id = userNode.get("id").asText();
                String email = userNode.get("email").asText();
                String role = userNode.get("role").asText();
                String firstName = userNode.get("firstName").asText();
                String lastName = userNode.get("lastName").asText();

                UserAccount userAccount = new UserAccount(id, email, role, firstName, lastName);
                userAccounts.add(userAccount);
            }
            return FXCollections.observableArrayList(userAccounts);
        } else {
            System.err.println("Unexpected JSON structure. Unable to deserialize users.");
            return FXCollections.observableArrayList();
        }*/
        return FXCollections.observableArrayList();

    }
}
