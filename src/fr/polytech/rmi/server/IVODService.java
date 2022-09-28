package fr.polytech.rmi.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IVODService extends Remote {

    default void echo() throws RemoteException {
        System.out.println("I'm VOD service, it works!");
    }

     IVODService getService() throws RemoteException;
}
