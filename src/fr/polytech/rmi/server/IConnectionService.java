package fr.polytech.rmi.server;

import fr.polytech.rmi.server.exception.InvalidCredentialsException;
import fr.polytech.rmi.server.exception.SignInFailedException;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IConnectionService extends Remote {

    boolean signIn(String mail, String pwd) throws SignInFailedException, RemoteException;

    IVODService login(String mail, String pwd) throws InvalidCredentialsException, RemoteException;

}