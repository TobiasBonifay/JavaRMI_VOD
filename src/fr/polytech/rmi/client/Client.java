package fr.polytech.rmi.client;


import fr.polytech.rmi.server.exception.InvalidCredentialsException;
import fr.polytech.rmi.server.exception.SignInFailedException;
import fr.polytech.rmi.server.interfaces.IConnectionService;
import fr.polytech.rmi.server.interfaces.IVODService;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.logging.Logger;


public class Client implements Serializable {

    private static final Scanner SCANNER = new Scanner(System.in);
    private static final Logger LOGGER = Logger.getLogger(Client.class.getName());
    private static final String IP_ADDRESS = "127.0.0.1";
    private static final int PORT = 1099;

    public static void main(String[] args) throws NotBoundException, RemoteException {
        Client c1 = new Client();
        c1.runClient();
        LOGGER.info("Finished");
    }

    private void runClient() throws RemoteException, NotBoundException {

        final Registry reg = LocateRegistry.getRegistry(IP_ADDRESS, PORT);
        final IConnectionService stubConnexion = (IConnectionService) reg.lookup("ConnexionServ");
        LOGGER.info("Establishing connection...");

        System.out.print("Email : ");
        final String email = SCANNER.nextLine();
        System.out.print("Password : ");
        final String pass = SCANNER.nextLine();
        SCANNER.close();

        try {
            stubConnexion.signIn(email, pass);
            LOGGER.info("The account is now signed in");
        } catch (SignInFailedException e) {
            LOGGER.severe("Failed to sign in");
            throw new RuntimeException(e);
        }

        try {
            final IVODService vodService = stubConnexion.login(email, pass);
            try {
                byte[] data = vodService.flow();
                LOGGER.info("Data received length : " + data.length);
            } catch (IOException e) {
                LOGGER.severe("vodService can't read the requested content.\n" + e);
            }
        } catch (InvalidCredentialsException e) {
            throw new RuntimeException("Invalid credentials\n" + e);
        }
    }
}
