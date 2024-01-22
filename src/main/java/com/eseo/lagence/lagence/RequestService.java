package com.eseo.lagence.lagence;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
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

    public boolean login(String username, String password) {
        String endpoint = baseUrl + "/auth/login";

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

            if (userNode != null && userNode.get("role").asText().equals("admin")) {
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

    public void logout() {
        String endpoint = baseUrl + "/auth/logout";

        HttpClient client = HttpClient.newHttpClient();

        // Create a login request
        HttpRequest logoutRequest = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .header("Cookie", this.cookieString)
                .POST(HttpRequest.BodyPublishers.ofString(""))
                .build();

        try {
            System.out.println("try logout");
            // Send the login request
            HttpResponse<String> logoutResponse = client.send(logoutRequest, HttpResponse.BodyHandlers.ofString());
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

    public ObservableList<Rental> getTenants() {

        JsonNode rootNode = sendHttpRequest("/user/rental", HttpMethod.GET);


        if (rootNode != null && rootNode.isArray()) {
            List<Rental> rentals = new ArrayList<>();

            for (JsonNode userNode : rootNode) {
                JsonNode propertyNode = userNode.get("rentedProperty");

                String id = userNode.get("id").asText();

                String accomodationId = propertyNode.get("id").asText();
                String name = propertyNode.get("name").asText();
                Double price = propertyNode.get("price").asDouble();
                String description = propertyNode.get("description").asText();
                String address = propertyNode.get("address").asText();
                Integer roomsCount = propertyNode.get("roomsCount").asInt();
                Integer surface = propertyNode.get("surface").asInt();

                String email = userNode.get("email").asText();
                String role = userNode.get("role").asText();
                String firstName = userNode.get("firstName").asText();
                String lastName = userNode.get("lastName").asText();
                Rental rental = new Rental(id,new Accommodation(accomodationId, name, price, description, address, roomsCount, surface), new UserAccount(id, email, firstName, lastName));
                rentals.add(rental);
            }
            return FXCollections.observableArrayList(rentals);
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
        List<RequestAccommodation> requestsAccommodation = new ArrayList<>();

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

                String userId = userNode.get("id").asText();
                String email = userNode.get("email").asText();
                String role = userNode.get("role").asText();
                String firstName = userNode.get("firstName").asText();
                String lastName = userNode.get("lastName").asText();

                Accommodation accommodation = new Accommodation(accomodationId, name, price, description, address, roomsCount, surface);
                UserAccount userAccount = new UserAccount(userId, email, role, firstName, lastName);
                System.out.println(state);

                if (state.equals("pending")){
                    requestsAccommodation.add(new RequestAccommodation(id, motivationText, idCardPath, proofOfAddressPath, state, userAccount, accommodation));
                }
            } else {
                System.err.println("Unexpected JSON structure. Unable to deserialize apply.");
                return FXCollections.observableArrayList();
            }

        }
        return FXCollections.observableArrayList(requestsAccommodation);
    }
    public void downloadPicture(String dossierPath){
        String urlPath = baseUrl + dossierPath;
        System.out.println("Téléchargement du dossier: " + dossierPath);
        String fileName = dossierPath;
        fileName.substring("uploads/".length());
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File");
        fileChooser.setInitialFileName(fileName);

        File selectedFile = fileChooser.showSaveDialog(null);

        if (selectedFile != null) {
            try {
                URI uri = new URI(urlPath);
                URL url = uri.toURL();
                var inputStream = url.openStream();
                var destination = selectedFile.toPath();
                Files.copy(inputStream, destination, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("File downloaded and saved to: " + destination);
            } catch (URISyntaxException | MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
