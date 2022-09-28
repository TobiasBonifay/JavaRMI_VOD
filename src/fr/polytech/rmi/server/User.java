package fr.polytech.rmi.server;

import fr.polytech.rmi.client.Client;

public class User {

    private String email;
    private String password;

    public User(String email, String password){
        this.email = email;
        this.password = password;
    }

}
