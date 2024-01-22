package com.eseo.lagence.lagence.services;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class AuthService {

    private static String performLogin(String email, String password) throws IOException {
        String loginUrl = "https://localhost:3000/auth/login";

        URL url = new URL(loginUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");

        String jsonInputString = "{\"email\":" +email + ",\"password\":" + password + "}";

        try(OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

//        try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
//            wr.writeBytes(postData);
//        }

        // Retreive cookie from response
        String cookies = connection.getHeaderField("Set-Cookie");

        connection.disconnect();

        return cookies;
    }
}
