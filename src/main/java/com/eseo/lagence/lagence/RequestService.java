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
import java.util.Map;


public class RequestService {
    private static RequestService instance;

    private String cookieString;

    private String baseUrl = "http://localhost:3000/api";

    public enum HttpMethod{
        GET,
        POST,
        DELETE
    }

    public static RequestService getInstance() {
        if(RequestService.instance == null) {
            RequestService.instance = new RequestService();
        }
        return RequestService.instance;
    }

    public void login(String loginEndpoint, String username, String password) {
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

            Map<String, List<String>> headers = loginResponse.headers().map();
            List<String> cookies = headers.get("Set-Cookie");
            String cookieString = String.join("; ", cookies);
            this.cookieString = cookieString;
            return;

        } catch (Exception e) {
            e.printStackTrace();
            return;
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
            this.cookieString = null;
            return;

        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    public JsonNode sendHttpRequest(String endpoint, HttpMethod httpMethod) {
        HttpClient httpClient = HttpClient.newHttpClient();
        ObjectMapper objectMapper = new ObjectMapper();
        String fullEndpoint = baseUrl + endpoint;
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(fullEndpoint));
        if (this.cookieString != null){
            requestBuilder = requestBuilder.header("Cookie", this.cookieString);
        }

        switch (httpMethod) {
            case GET:
                requestBuilder = requestBuilder.GET();
                break;
            case POST:
                requestBuilder = requestBuilder.POST(HttpRequest.BodyPublishers.noBody());
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

        JsonNode rootNode = sendHttpRequest("/property", HttpMethod.GET);

            JsonNode propertiesNode = rootNode.get("properties");

            if (propertiesNode != null && propertiesNode.isArray()) {
                List<Accommodation> accommodations = new ArrayList<>();

                for (JsonNode accommodationNode : propertiesNode) {
                    String id = accommodationNode.get("id").asText();
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
                System.err.println("Unexpected JSON structure. Unable to deserialize accommodations.");
                return FXCollections.observableArrayList();
            }
    }

    public ObservableList<UserAccount> getUsers() {

        JsonNode rootNode = sendHttpRequest("/user", HttpMethod.GET);
        System.out.println(rootNode);

        if (rootNode != null && rootNode.isArray()) {
            List<UserAccount> userAccounts = new ArrayList<>();

            for (JsonNode userNode : rootNode) {
                String id = userNode.get("id").asText();
                String email = userNode.get("email").asText();
                String firstName = userNode.get("firstName").asText();
                String lastName = userNode.get("lastName").asText();

                UserAccount userAccount = new UserAccount(id, email, firstName, lastName);
                userAccounts.add(userAccount);
            }
            return FXCollections.observableArrayList(userAccounts);
        } else {
            System.err.println("Unexpected JSON structure. Unable to deserialize users.");
            return FXCollections.observableArrayList();
        }
    }
}
