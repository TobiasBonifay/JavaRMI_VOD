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

public class Client implements Serializable {

    private static final String IPADDRESS = "127.0.0.1";
    private static final int PORT = 1099;

    public static void main(String[] args) throws NotBoundException, RemoteException {
        Client c1 = new Client();
        c1.runClient();
        System.out.println("Finished");
    }

    private void runClient() throws RemoteException, NotBoundException {

        final Registry reg = LocateRegistry.getRegistry(IPADDRESS, PORT);
        final IConnectionService stubConnexion = (IConnectionService) reg.lookup("ConnexionServ");
        System.out.println("Establishing connection...");

        final Scanner myScanner = new Scanner(System.in);
        System.out.print("Email : ");
        final String email = myScanner.nextLine();
        System.out.print("Password : ");
        final String pass = myScanner.nextLine();

        try {
            stubConnexion.signIn(email, pass);
            System.out.println("The account is now signed in");
        } catch (SignInFailedException e) {
            throw new RuntimeException(e);
        }

        try {
            final IVODService vodService = stubConnexion.login(email, pass);
            try {
                byte[] data = vodService.flow();
                System.out.println("Data received length : " + data.length);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (InvalidCredentialsException e) {
            throw new RuntimeException(e);
        }
    }
}
