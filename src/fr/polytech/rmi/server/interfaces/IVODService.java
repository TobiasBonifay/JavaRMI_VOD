package fr.polytech.rmi.server.interfaces;

import fr.polytech.rmi.server.MovieDesc;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IVODService extends Remote {

    default void echo() throws RemoteException {
        System.out.println("I'm VOD service, it works!");
    }

    List<MovieDesc> viewCatalog() throws RemoteException;

    byte[] flow() throws RemoteException, IOException;
}
