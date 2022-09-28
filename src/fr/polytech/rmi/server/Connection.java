package fr.polytech.rmi.server;

import fr.polytech.rmi.server.exception.InvalidCredentialsException;
import fr.polytech.rmi.server.exception.SignInFailedException;
import fr.polytech.rmi.server.interfaces.IConnectionService;
import fr.polytech.rmi.server.interfaces.IVODService;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Connection extends UnicastRemoteObject implements IConnectionService {

    private List<User> clientList;
    private VODService vodService;

    public Connection() throws RemoteException {
        this.clientList = new ArrayList<>();
        this.vodService = new VODService();
    }

    @Override
    public void run() throws RemoteException {
        Scanner myScanner = new Scanner(System.in);
        String email = myScanner.nextLine();
        String pass = myScanner.nextLine();
        try {
            if (signIn(email, pass)){
                System.out.println("User was successfully added to client list !");
            }
        } catch (SignInFailedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean signIn(String mail, String password) throws SignInFailedException, RemoteException {
        if (mail == null || password == null) return false;

        if (mail.isBlank())
            throw new SignInFailedException("Mail is empty");

        if (password.isBlank())
            throw new SignInFailedException("Pass is empty");

        if (clientList.stream().anyMatch(u -> u.getEmail().equals(mail))) {
            System.out.println("User was already added to client list");
            return false;
        }

        final User newUser = new User(mail, password);
        return clientList.add(newUser);
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
