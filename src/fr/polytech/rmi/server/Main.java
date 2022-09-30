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
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
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
        try {
            final File file = new File(path.toUri());
            final Set<User> userToRecreate = new HashSet<>();
            if (file.exists() && !file.isDirectory() && file.canRead()) {
                LOGGER.info("File exists");
                final Scanner scanner = new Scanner(file);
                LOGGER.info("Users found:");
                // each string represents an user (email password)
                while (scanner.hasNextLine()) {
                    final String[] data = scanner.nextLine().split(" ");
                    final User userFound = new User(data[0], data[1]);
                    userToRecreate.add(userFound);
                }
            }
            // USER LIST TO GIVE !! TO CONTINUE
            System.out.println(userToRecreate.toArray().toString());

        } catch (FileNotFoundException e) {
            LOGGER.info("No old save DB detected");
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
            for (User client : ic.getClients())
                writer.write(client.getEmail() + " " + client.getPassword() + System.lineSeparator());
            writer.close();
        } catch (IOException | NotBoundException e) {
            e.printStackTrace();
        } finally {
            LOGGER.info("Shutdown.");
        }
    }
}