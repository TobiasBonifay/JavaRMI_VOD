package fr.polytech.rmi.server;

import fr.polytech.rmi.server.exception.InvalidCredentialsException;
import fr.polytech.rmi.server.exception.SignInFailedException;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class Connection implements IConnectionService {

    private List<User> clientList;

    public Connection() {
        this.clientList = new ArrayList<>();
    }

    @Override
    public boolean signIn(String mail, String password) throws SignInFailedException, RemoteException {
        if (mail.isBlank())
            throw new SignInFailedException("Mail is empty");
        clientList.add(new User(mail, password));
        return false;
    }

    @Override
    public IVODService login(String mail, String password) throws InvalidCredentialsException,RemoteException {
        if (mail.isBlank())
            throw new InvalidCredentialsException("Mail is empty");

        return new VODService();
    }
}
