package fr.polytech.rmi.server;

import fr.polytech.rmi.CONSTANTS;
import fr.polytech.rmi.server.interfaces.IVODService;

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
            final IVODService obj = new VODService();
            // Bind the remote object's stub in the registry
            final Registry registry = LocateRegistry.createRegistry(CONSTANTS.DEFAULT_PORT);
            registry.bind(CONSTANTS.NAME, obj);
            System.out.println("Server ready");
        } catch (RemoteException e) {
            System.err.println("Server exception: " + e);
        } catch (Exception e) {
            System.err.println("Unexpected server error");
            e.printStackTrace();
        }
    }
}