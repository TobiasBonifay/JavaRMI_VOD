package fr.polytech.rmi.server;

import fr.polytech.rmi.CONSTANTS;
import fr.polytech.rmi.server.interfaces.IConnectionService;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Logger;

/**
 * Server launcher
 *
 * @author Tobias Bonifay
 * @author Ihor Melnyk
 */
public class Main {

    private static final java.util.logging.Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {

        LOGGER.info("Server is starting...");

        try {
            final IConnectionService connectionService = new Connection();
            LOGGER.info(" ConnectionServer is running... ");
            final Registry reg = LocateRegistry.createRegistry(CONSTANTS.DEFAULT_PORT);
            reg.bind(CONSTANTS.CONNEXIONSERV, connectionService);
            LOGGER.info("Server ready");
        } catch (RemoteException e) {
            LOGGER.severe("Server exception: " + e);
        } catch (AlreadyBoundException e) {
            LOGGER.severe("Connection already bound: " + e);
        }
    }
}