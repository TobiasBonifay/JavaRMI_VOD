package fr.polytech.rmi.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class MovieDesc extends UnicastRemoteObject {
    protected String isbn;
    protected String movieName;
    protected String synopsis;

    public MovieDesc(String isbn, String movieName, String synopsis) throws RemoteException {
        this.isbn = isbn;
        this.movieName = movieName;
        this.synopsis = synopsis;
    }
}
