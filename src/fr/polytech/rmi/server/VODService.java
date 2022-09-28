package fr.polytech.rmi.server;

import fr.polytech.rmi.client.IClientBox;
import fr.polytech.rmi.server.interfaces.IVODService;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class VODService extends UnicastRemoteObject implements IVODService, Serializable {

    protected VODService() throws RemoteException {
        super();
    }

    private List<MovieDesc> viewCatalog() throws RemoteException {
        return new ArrayList<>();
    }

    Bill playMovie(String isbn, IClientBox box) throws RemoteException {
        return new Bill(isbn, box);
    }

    @Override
    public void echo() throws RemoteException {
        IVODService.super.echo();
    }

    @Override
    public IVODService getService() throws RemoteException {
        return null;
    }
}
