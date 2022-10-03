package fr.polytech.rmi.server;

import fr.polytech.rmi.server.interfaces.IConnectionService;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashSet;
import java.util.Objects;
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

    /**
     * CONFIGURATION
     */
    private static final String CONNEXION_SERV = "ConnexionServ";
    private static final String FILE_DB = "save.db";
    private static final int DEFAULT_PORT = 1099;
    private static final Path path = Paths.get(FILE_DB);

    private static final java.util.logging.Logger LOGGER = Logger.getLogger(Main.class.getName());

    /**
     * Run the server & import user from FILE_DB
     *
     * @param args no parameters are required
     */
    public static void main(String[] args) {

        LOGGER.info("Server is starting...");
        LOGGER.info("Looking for old database file... " + FILE_DB);
        final File file = new File(path.toUri());
        final Set<User> userToRecreate = importUserFromFile(file);

        initiateConnection(userToRecreate);
    }

    /**
     * Create a connection, bind to a specific port, and handle the shutdown hook
     *
     * @param userToRecreate Set of user to import
     */
    private static void initiateConnection(Set<User> userToRecreate) {
        Registry reg = null;
        try {
            final IConnectionService connectionService = new Connection(userToRecreate);
            LOGGER.info(" ConnectionServer is running... ");
            reg = LocateRegistry.createRegistry(DEFAULT_PORT);
            reg.bind(CONNEXION_SERV, connectionService);
            LOGGER.info("Server ready");
        } catch (RemoteException e) {
            LOGGER.severe("Server exception: " + e);
        } catch (AlreadyBoundException e) {
            LOGGER.severe("Connection already bound: " + e);
        } finally {
            final Registry finalReg = reg;
            final Thread saveDB = new Thread(() -> save(Objects.requireNonNull(finalReg)));
            Runtime.getRuntime().addShutdownHook(saveDB);
        }
    }

    /**
     * Fetch user from file
     *
     * @param file to import
     * @return Set of User
     */
    private static Set<User> importUserFromFile(final File file) {
        if (file == null || !file.exists() || file.isDirectory() || !file.canRead()) return new HashSet<>();

        final Set<User> userToRecreate = new HashSet<>();
        try {
            final Scanner scanner = new Scanner(file);
            LOGGER.info("File exists\nUsers found:");
            // each string represents a user (email password)
            while (scanner.hasNextLine()) {
                final String[] data = scanner.nextLine().split(" ");
                final User userFound = new User(data[0], data[1]);
                userToRecreate.add(userFound);
                LOGGER.info("User: " + userFound.getEmail() + "  " + userFound.getPassword());
            }
            scanner.close();
        } catch (IOException e) {
            LOGGER.info("Can't import " + file.getAbsolutePath());
        }
        return userToRecreate;
    }

    /**
     * Method executed on shutdown, write/export users in a file
     *
     * @param finalReg Registry
     */
    private static void save(final Registry finalReg) {
        LOGGER.info("Saving before shutdown...");
        try (final BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile()))) {
            final IConnectionService ic = (IConnectionService) finalReg.lookup(CONNEXION_SERV);
            for (User client : ic.getClients())
                writer.write(client.getEmail() + " " + client.getPassword() + System.lineSeparator());
        } catch (IOException | NotBoundException e) {
            e.printStackTrace();
        } finally {
            LOGGER.info("Shutdown.");
        }
    }
}