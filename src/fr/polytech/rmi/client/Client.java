package fr.polytech.rmi.client;


import fr.polytech.rmi.server.Bill;
import fr.polytech.rmi.server.exception.InvalidCredentialsException;
import fr.polytech.rmi.server.exception.SignInFailedException;
import fr.polytech.rmi.server.interfaces.IConnectionService;
import fr.polytech.rmi.server.interfaces.IVODService;

import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Client launcher
 *
 * @author Tobias Bonifay
 * @author Ihor Melnyk
 */
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
        final Client c1 = new Client();
        c1.runClient();
        LOGGER.info("Finished");
    }

    /**
     * Allow the user to enter mail and pass on the console
     */
    private void enterCredentials() {
        System.out.print("Email: ");
        this.email = SCANNER.nextLine();
        System.out.print("Password: ");
        this.password = SCANNER.nextLine();
    }

    void runClient() throws RemoteException, NotBoundException {

        final IVODService vodService;
        final Registry reg = LocateRegistry.getRegistry(IP_ADDRESS, PORT);
        final IConnectionService stubConnexion = (IConnectionService) reg.lookup("ConnexionServ");
        LOGGER.info("Establishing connection...");

        signIn(stubConnexion);

        try {
            vodService = stubConnexion.login(email, password); // login
            vodService.viewCatalog().forEach(System.out::println); // show catalog

            System.out.println("Write isbn : ");
            final String isbn = SCANNER.nextLine().toLowerCase().trim(); // get isbn
            final Bill bill = vodService.playMovie(isbn, this); // play the isbn
            System.out.println(bill); // show the bill
        } catch (InvalidCredentialsException e) {
            LOGGER.severe("Invalid credentials");
        }
    }

    private void signIn(IConnectionService stubConnexion) throws RemoteException {

        String choice;
        do {
            // waiting the user to answer y/n
            System.out.println("Do you have an account ? (y/n)");
            choice = SCANNER.nextLine().trim().toLowerCase();
        } while (!(choice.equals("y") || choice.equals("n")));

        System.out.println(choice.equals("y") ? "Login : " : "Account creation : ");
        enterCredentials();        // input login & password is necessary in both case

        if (choice.equals("n")) {        // if user does not own any account, create one
            try {
                stubConnexion.signIn(email, password);
            } catch (SignInFailedException e) {
                LOGGER.severe("Failed to sign in with " + email);
            }
        }
    }

    //Used by server side
    @Override
    public void stream(byte[] chunk) throws RemoteException {
        System.out.println("Playing the video:  ");
        System.out.println(Arrays.toString(chunk));
    }
}
