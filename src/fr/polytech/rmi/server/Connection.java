package fr.polytech.rmi.server;

import fr.polytech.rmi.server.exception.InvalidCredentialsException;
import fr.polytech.rmi.server.exception.SignInFailedException;
import fr.polytech.rmi.server.interfaces.IConnectionService;
import fr.polytech.rmi.server.interfaces.IVODService;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class Connection implements IConnectionService {

    private List<User> clientList;
    private VODService vodService;

    public Connection() {
        this.clientList = new ArrayList<>();
        this.vodService = new VODService();
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
        for (User c : clientList){
            if (c.getEmail().equals(mail) && c.getPassword().equals(password)){
                return this.vodService;
            }
        }
        return new VODService();
    }
}
