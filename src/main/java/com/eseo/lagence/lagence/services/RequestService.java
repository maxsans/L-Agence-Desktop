package com.eseo.lagence.lagence.services;

import com.eseo.lagence.lagence.models.UserAccount;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.stage.FileChooser;
import org.apache.hc.client5.http.classic.methods.HttpUriRequest;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.EntityUtils;

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

    public JsonNode sendHttpRequest(HttpUriRequest request) {
        ObjectMapper objectMapper = new ObjectMapper();

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // Set the full URI for the request
            URI fullUri = URI.create(baseUrl + request.getUri());
            request.setUri(fullUri);

            // Add the Cookie header if present
            if (this.cookieString != null) {
                request.setHeader("Cookie", this.cookieString);
            }

            // Send the request and handle the response
            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {

                System.out.println("Status Code: " + httpResponse.getCode());
                String responseBody = EntityUtils.toString(httpResponse.getEntity());
                System.out.println("Response Body:\n" + responseBody);
                return objectMapper.readTree(responseBody);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return objectMapper.createObjectNode();
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

    public String getCookieString() {
        return cookieString;
    }

    protected void setCookieString(String cookieString) {
        this.cookieString = cookieString;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    protected void setUserLogged(UserAccount userLogged) {
        this.userLogged = userLogged;
    }

    public UserAccount getUserLogged() {
        return userLogged;
    }

    public void downloadPicture(String dossierPath) {
        String urlPath = baseUrl + dossierPath;
        System.out.println("Téléchargement du dossier: " + dossierPath);
        String fileName = dossierPath;
//        fileName.substring("uploads/".length());
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
