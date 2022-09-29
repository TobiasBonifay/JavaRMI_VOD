package fr.polytech.rmi.server;

import fr.polytech.rmi.CONSTANTS;
import fr.polytech.rmi.server.interfaces.IConnectionService;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Server launcher
 *
 * @author Tobias Bonifay
 * @author Ihor Melnyk
 */
public class Main {

    public static void main(String[] args) {

        System.out.println("Server is starting...");

        try {
            System.out.println(" ConnectionServer is running... ");
            final IConnectionService connectionService = new Connection();
            final Registry reg = LocateRegistry.createRegistry(CONSTANTS.DEFAULT_PORT);
            reg.bind(CONSTANTS.CONNEXIONSERV, connectionService);
            System.out.println("Server ready");
        } catch (RemoteException e) {
            System.err.println("Server exception: " + e);
        } catch (AlreadyBoundException e) {
            System.err.println("Unexpected server error");
            e.printStackTrace();
        }
    }
}