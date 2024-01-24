package com.eseo.lagence.lagence.services;

import com.eseo.lagence.lagence.models.UserAccount;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

public class AuthService{

    public static boolean login(String username, String password) {
        String endpoint = RequestService.getInstance().getBaseUrl() + "/auth/login";

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
                RequestService.getInstance().setUserLogged(new UserAccount(id, email, role, firstName, lastName));
            } else {
                System.err.println("Unexpected JSON structure. Unable to deserialize users.");
                return false;
            }

            Map<String, List<String>> headers = loginResponse.headers().map();
            List<String> cookies = headers.get("Set-Cookie");
            String cookieString = String.join("; ", cookies);
            RequestService.getInstance().setCookieString(cookieString);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            RequestService.getInstance().setUserLogged(null);
            RequestService.getInstance().setCookieString(null);
            return false;
        }
    }
    public static void logout() {
        String endpoint = RequestService.getInstance().getBaseUrl() + "/auth/logout";

        HttpClient client = HttpClient.newHttpClient();

        // Create a login request
        HttpRequest logoutRequest = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .header("Cookie", RequestService.getInstance().getCookieString())
                .POST(HttpRequest.BodyPublishers.ofString(""))
                .build();

        try {
            System.out.println("try logout");
            // Send the login request
            HttpResponse<String> logoutResponse = client.send(logoutRequest, HttpResponse.BodyHandlers.ofString());
            RequestService.getInstance().setUserLogged(null);
            RequestService.getInstance().setCookieString(null);
            return;

        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
}
