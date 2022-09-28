package fr.polytech.rmi.server;

import fr.polytech.rmi.CONSTANTS;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Server launcher
 * @author Tobias Bonifay
 * @author Ihor Melnyk
 */
public class Main {

    public static void main(String[] args) {
        try {
            final IVODService obj = new VODService();
            // Bind the remote object's stub in the registry
            final Registry registry = LocateRegistry.createRegistry(CONSTANTS.DEFAULT_PORT);
            registry.bind(CONSTANTS.NAME, obj);

            System.err.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e);
            e.printStackTrace();
        }
    }
}