package fr.polytech.rmi.server;

import fr.polytech.rmi.server.exception.InvalidCredentialsException;
import fr.polytech.rmi.server.exception.SignInFailedException;
import fr.polytech.rmi.server.interfaces.IConnectionService;
import fr.polytech.rmi.server.interfaces.IVODService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Logger;

public class Connection extends UnicastRemoteObject implements IConnectionService {

    private static final java.util.logging.Logger LOGGER = Logger.getLogger(Connection.class.getName());
    private static final Scanner SCANNER = new Scanner(System.in);
    private static VODService vodService;
    private final transient Set<User> clients;

    public Connection() throws RemoteException {
        vodService = new VODService();
        this.clients = new HashSet<>();
    }

    @Override
    public void run() throws RemoteException {
        final String email = SCANNER.nextLine();
        final String pass = SCANNER.nextLine();
        try {
            if (signIn(email, pass)) System.out.println("User was successfully added to client list !");
        } catch (SignInFailedException e) {
            LOGGER.severe("User was not added to client list... already logged in? " + e);
        }
    }

    @Override
    public boolean signIn(String mail, String password) throws SignInFailedException, RemoteException {
        if (mail == null || mail.isBlank()) throw new SignInFailedException("Mail is empty");
        if (password == null || password.isBlank()) throw new SignInFailedException("Password is empty");

        if (clients.stream().anyMatch(u -> u.getEmail().equals(mail)))
            throw new SignInFailedException("User was already added to client list");

        final User newUser = new User(mail, password);
        return clients.add(newUser);
    }

    @Override
    public IVODService login(String mail, String password) throws InvalidCredentialsException, RemoteException {
        if (mail == null || mail.isBlank()) throw new InvalidCredentialsException("Mail is empty");
        if (password == null || password.isBlank()) throw new InvalidCredentialsException("Password is empty");
        return clients.stream().anyMatch(u -> u.getEmail().equals(mail) && u.getPassword().equals(password)) ? vodService : null;
    }
}
