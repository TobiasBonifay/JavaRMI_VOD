package fr.polytech.rmi.server.interfaces;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IVODService extends Remote {

    default void echo() throws RemoteException {
        System.out.println("I'm VOD service, it works!");
    }

    IVODService getService() throws RemoteException;

    byte[] flow() throws RemoteException, IOException;
}
