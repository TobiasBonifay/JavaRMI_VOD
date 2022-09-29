package fr.polytech.rmi.server;

import fr.polytech.rmi.CONSTANTS;
import fr.polytech.rmi.server.interfaces.IConnectionService;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
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

        Registry reg = null;
        try {
            final IConnectionService connectionService = new Connection();
            LOGGER.info(" ConnectionServer is running... ");
            reg = LocateRegistry.createRegistry(CONSTANTS.DEFAULT_PORT);
            reg.bind(CONSTANTS.CONNEXIONSERV, connectionService);
            LOGGER.info("Server ready");
        } catch (RemoteException e) {
            LOGGER.severe("Server exception: " + e);
        } catch (AlreadyBoundException e) {
            LOGGER.severe("Connection already bound: " + e);
        } finally {
            final Registry finalReg = reg;
            final Thread saveDB = new Thread(() -> save(finalReg));
            Runtime.getRuntime().addShutdownHook(saveDB);
        }
    }

    private static void save(final Registry finalReg) {
        try {
            LOGGER.info("Saving before shutdown...");
            final FileWriter fw = new FileWriter("save.db");
            final BufferedWriter writer = new BufferedWriter(fw);
            final IConnectionService ic = (IConnectionService) finalReg.lookup(CONSTANTS.CONNEXIONSERV);
            ic.getClients().stream().forEach(client -> {
                try {
                    writer.write(client.getEmail() + " " + client.getPassword());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            writer.close();
        } catch (IOException | NotBoundException e) {
            e.printStackTrace();
        } finally {
            LOGGER.info("Shutdown.");
        }
    }
}