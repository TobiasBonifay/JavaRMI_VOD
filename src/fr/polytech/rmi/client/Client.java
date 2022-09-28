package fr.polytech.rmi.client;


import fr.polytech.rmi.server.exception.SignInFailedException;
import fr.polytech.rmi.server.interfaces.IConnectionService;

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

        try {


            final Registry reg = LocateRegistry.getRegistry(IPADDRESS, PORT);
            final IConnectionService stubConnexion = (IConnectionService) reg.lookup("ConnexionServ");
            System.out.println("Connexion établie...");

            Scanner myScanner = new Scanner(System.in);
            System.out.print("Email : ");
            String email = myScanner.nextLine();
            System.out.print("Password : ");
            String pass = myScanner.nextLine();
            if (stubConnexion.signIn(email, pass)){
                System.out.println("Compte a été créé");
            }

        } catch (RemoteException | NotBoundException | SignInFailedException e){
            e.printStackTrace();
        }
        /*
        final Registry r = LocateRegistry.getRegistry(IPADDRESS, PORT);
        final IVODService stub = (IVODService) r.lookup(CONSTANTS.NAME);
        stub.echo()*/
    }
}
