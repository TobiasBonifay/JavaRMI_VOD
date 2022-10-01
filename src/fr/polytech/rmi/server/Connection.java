package fr.polytech.rmi.server;

import fr.polytech.rmi.server.exception.InvalidCredentialsException;
import fr.polytech.rmi.server.exception.SignInFailedException;
import fr.polytech.rmi.server.interfaces.IConnectionService;
import fr.polytech.rmi.server.interfaces.IVODService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;

public class Connection extends UnicastRemoteObject implements IConnectionService {

    private static final java.util.logging.Logger LOGGER = Logger.getLogger(Connection.class.getName());
    private static final Scanner SCANNER = new Scanner(System.in);
    private static VODService vodService;

    static {
        try {
            vodService = new VODService();
        } catch (RemoteException e) {
            LOGGER.severe("Can't create the VOD service.");
        }
    }

    private final transient Set<User> clients;

    public Connection(final Set<User> clients) throws RemoteException {
        this.clients = clients;
    }

    @Override
    public Set<User> getClients() {
        return this.clients;
    }

    @Override
    public void run() throws RemoteException {
        final String email = SCANNER.nextLine();
        final String pass = SCANNER.nextLine();
        try {
            if (signIn(email, pass)) LOGGER.info("User was successfully added to client list !");
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
        final Predicate<User> sameMailAndPassword = u -> u.getEmail().equals(mail) && u.getPassword().equals(password);
        if (clients.stream().anyMatch(sameMailAndPassword))
            return vodService;

        throw new InvalidCredentialsException("User does not exists.");
    }
}
