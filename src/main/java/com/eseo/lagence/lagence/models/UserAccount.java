package com.eseo.lagence.lagence.models;

import java.util.List;

public class UserAccount {

    private String id;
    private String email;
    private String role;

    private String firstName;
    private String lastName;

    public UserAccount(String id, String email,
                       String firstName, String lastName) {
        this.id = id;
        this.email = email;
        this.role = "user";
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UserAccount(String id, String email, String role,
                       String firstName, String lastName) {
        this.id = id;
        this.email = email;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}
