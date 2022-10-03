package fr.polytech.rmi.client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Client launcher 2 to test multi-thread
 *
 * @author Tobias Bonifay
 * @author Ihor Melnyk
 */
public class MainClient2 {

    public static void main(String[] args) throws RemoteException, NotBoundException {
        final Client c2 = new Client();
        c2.runClient();
        System.out.println("Done.");
    }
}
