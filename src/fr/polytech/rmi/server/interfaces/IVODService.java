package fr.polytech.rmi.server.interfaces;

import fr.polytech.rmi.client.IClientBox;
import fr.polytech.rmi.server.Bill;
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

    Bill playMovie(String isbn, IClientBox box) throws RemoteException;

    byte[] flow(String isbn) throws RemoteException, IOException;
}
