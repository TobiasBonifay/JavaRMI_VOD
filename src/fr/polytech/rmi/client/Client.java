package fr.polytech.rmi.client;


import fr.polytech.rmi.server.Bill;
import fr.polytech.rmi.server.MovieDesc;
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
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;


public class Client extends UnicastRemoteObject implements Serializable, IClientBox {

    private static final Scanner SCANNER = new Scanner(System.in);
    private static final Logger LOGGER = Logger.getLogger(Client.class.getName());
    private static final String IP_ADDRESS = "127.0.0.1";
    private static final int PORT = 1099;
    private String email;
    private String password;

    protected Client() throws RemoteException {
    }

    public static void main(String[] args) throws NotBoundException, RemoteException {
        Client c1 = new Client();
        c1.runClient();
        LOGGER.info("Finished");
    }

    private void enterCredentials() {
        System.out.print("Email : ");
        this.email = SCANNER.nextLine();
        System.out.print("Password : ");
        this.password = SCANNER.nextLine();
    }

    private void runClient() throws RemoteException, NotBoundException {

        final Registry reg = LocateRegistry.getRegistry(IP_ADDRESS, PORT);
        final IConnectionService stubConnexion = (IConnectionService) reg.lookup("ConnexionServ");
        LOGGER.info("Establishing connection...");

        signIn(stubConnexion);

        final IVODService vodService;
        try {
            vodService = stubConnexion.login(email, password);
        } catch (InvalidCredentialsException e) {
            throw new RuntimeException("Invalid credentials\n" + e);
        }
        try {
            // Client initialement connait donc la classe MovieDesc
            List<MovieDesc> movieDescList = vodService.viewCatalog();
            for (MovieDesc m : movieDescList) {
                System.out.println(m);
            }

            System.out.println("Write isbn (for now), example.mp4: ");
            String isbn = SCANNER.nextLine();
            Bill bill = vodService.playMovie(isbn, this);
            System.out.println(bill);

        } catch (IOException e) {
            LOGGER.severe("vodService can't read the requested content.\n" + e);
        }


    }

    private void signIn(IConnectionService stubConnexion) throws RemoteException {
        String choice;
        do {
            System.out.println("Do you have an account ? (y/n)");
            choice = SCANNER.nextLine().trim().toLowerCase();
        } while (!(choice.equals("y") || choice.equals("n")));

        System.out.println(choice.equals("y") ? "Login : " : "Account creation : ");
        enterCredentials();

        if (choice.equals("n")) {
            try {
                stubConnexion.signIn(email, password);
            } catch (SignInFailedException e) {
                LOGGER.severe("Failed to sign in");
                throw new RuntimeException(e);
            }
        }
    }

    //Used by server side
    @Override
    public void stream(byte[] chunk) throws RemoteException {
        System.out.println("Lecture de la vidéo : ");
        System.out.println(Arrays.toString(chunk));
    }
}
