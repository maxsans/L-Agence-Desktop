package com.eseo.lagence.lagence.services;

import com.eseo.lagence.lagence.models.UserAccount;
import com.fasterxml.jackson.databind.JsonNode;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserService {

    public static ObservableList<UserAccount> getUsers() {
        JsonNode rootNode = RequestService.getInstance().sendHttpRequest("/user", RequestService.HttpMethod.GET, Optional.empty());
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

}
