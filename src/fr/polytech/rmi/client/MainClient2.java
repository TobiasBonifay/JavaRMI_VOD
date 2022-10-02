package fr.polytech.rmi.client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class MainClient2 {

    // Pour tester multi-thread
    public static void main(String[] args) {
        Client c2 = null;
        try {
            c2 = new Client();
            c2.runClient();
        } catch (RemoteException | NotBoundException e) {
            throw new RuntimeException(e);
        }
    }
}
