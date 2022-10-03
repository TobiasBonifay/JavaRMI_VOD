package fr.polytech.rmi.server.interfaces;

import fr.polytech.rmi.client.IClientBox;
import fr.polytech.rmi.server.Bill;
import fr.polytech.rmi.server.MovieDesc;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IVODService extends Remote {

    List<MovieDesc> viewCatalog() throws RemoteException;

    Bill playMovie(String isbn, IClientBox box) throws RemoteException;
}
