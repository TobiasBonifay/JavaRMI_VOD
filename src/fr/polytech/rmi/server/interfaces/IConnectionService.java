package fr.polytech.rmi.server.interfaces;

import fr.polytech.rmi.server.User;
import fr.polytech.rmi.server.exception.InvalidCredentialsException;
import fr.polytech.rmi.server.exception.SignInFailedException;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Set;

public interface IConnectionService extends Remote {

    /**
     * Allow the user to register using (mail, password)
     *
     * @param mail email of the user
     * @param pwd  password of the user
     * @return boolean value, true if signIn was successfully.
     * @throws SignInFailedException invalid mail/password
     * @throws RemoteException       RMI exception..
     */
    boolean signIn(String mail, String pwd) throws SignInFailedException, RemoteException;

    /**
     * Allow the user to login
     *
     * @param mail email of the user
     * @param pwd  password of the user
     * @return IVODService if credentials are valid.
     * @throws InvalidCredentialsException if credentials is not valid.
     * @throws RemoteException             rmi exception...
     */
    IVODService login(String mail, String pwd) throws InvalidCredentialsException, RemoteException;

    Set<User> getClients() throws RemoteException;
}