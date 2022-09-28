package fr.polytech.rmi.server;

import fr.polytech.rmi.client.IClientBox;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class VODService implements IVODService {

    private List<MovieDesc> viewCatalog() {
        return new ArrayList<>();
    }

    Bill playMovie(String isbn, IClientBox box) {
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
