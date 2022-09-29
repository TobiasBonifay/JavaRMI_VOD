package fr.polytech.rmi.server.interfaces;

import fr.polytech.rmi.server.User;
import fr.polytech.rmi.server.exception.InvalidCredentialsException;
import fr.polytech.rmi.server.exception.SignInFailedException;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Set;

public interface IConnectionService extends Remote {

    boolean signIn(String mail, String pwd) throws SignInFailedException, RemoteException;

    IVODService login(String mail, String pwd) throws InvalidCredentialsException, RemoteException;

    void run() throws RemoteException;

    Set<User> getClients() throws RemoteException;

}