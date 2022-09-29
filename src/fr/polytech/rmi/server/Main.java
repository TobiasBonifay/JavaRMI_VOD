package fr.polytech.rmi.server;

import fr.polytech.rmi.CONSTANTS;
import fr.polytech.rmi.server.interfaces.IConnectionService;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    private static final Path path = Paths.get(CONSTANTS.FILE_DB);

    private static final java.util.logging.Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {

        LOGGER.info("Server is starting...");

        LOGGER.info("Looking for configuration file... " + CONSTANTS.FILE_DB);
        InputStream inputStream = null;
        try {
            File file = new File(path.toUri());
            inputStream = new FileInputStream(file);
            if (file.exists() && !file.isDirectory() && file.canRead()) LOGGER.info("File exists");
            // TO DO with inputStream
        } catch (FileNotFoundException e) {
            LOGGER.info("No old save DB detected");
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

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
            final BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile()));
            final IConnectionService ic = (IConnectionService) finalReg.lookup(CONSTANTS.CONNEXIONSERV);
            for (User client : ic.getClients()) writer.write(client.getEmail() + " " + client.getPassword());
            writer.close();
        } catch (IOException | NotBoundException e) {
            e.printStackTrace();
        } finally {
            LOGGER.info("Shutdown.");
        }
    }
}