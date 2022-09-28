package fr.polytech.rmi.client;

import fr.polytech.rmi.CONSTANTS;
import fr.polytech.rmi.server.interfaces.IConnectionService;
import fr.polytech.rmi.server.interfaces.IVODService;

import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client implements Serializable {

    private static final String IPADDRESS = "127.0.0.1";
    private static final int PORT = 1099;

    public static void main(String[] args) throws NotBoundException, RemoteException {
        Client c1 = new Client();
        c1.runClient();
    }

    private void runClient() throws RemoteException, NotBoundException {
        final Registry r = LocateRegistry.getRegistry(IPADDRESS, PORT);
        final IVODService stub = (IVODService) r.lookup(CONSTANTS.NAME);

        IVODService vod = stub.getService();
        vod.echo();
    }
}
