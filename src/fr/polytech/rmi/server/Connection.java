package fr.polytech.rmi.server;

import fr.polytech.rmi.client.Client;
import fr.polytech.rmi.server.exception.InvalidCredentialsException;
import fr.polytech.rmi.server.exception.SignInFailedException;

import java.util.List;

public class Connection {

    private List<Client> clientList;


    public boolean signIn(String mail, String password) throws SignInFailedException {
        if (mail.isBlank())
            throw new SignInFailedException("Mail is empty");
        return false;
    }

    public IVODService login(String mail, String password) throws InvalidCredentialsException {
        if (mail.isBlank())
            throw new InvalidCredentialsException("Mail is empty");
        return new VODService();
    }
}
