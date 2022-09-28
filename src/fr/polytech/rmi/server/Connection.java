package fr.polytech.rmi.server;

import fr.polytech.rmi.server.exception.InvalidCredentialsException;
import fr.polytech.rmi.server.exception.SignInFailedException;

import java.util.List;

public class Connection {
    private List<Client> clientList;


    public boolean signIn(String mail, String password) throws SignInFailedException {

        return false;
    }

    public IVODService login(String mail, String password) throws InvalidCredentialsException {
        return new VODService();
    }
}
