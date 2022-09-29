package fr.polytech.rmi.server;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class MovieDesc extends UnicastRemoteObject implements Serializable {
    protected String isbn;
    protected String movieName;
    protected String synopsis;

    public MovieDesc(String isbn, String movieName, String synopsis) throws RemoteException {
        this.isbn = isbn;
        this.movieName = movieName;
        this.synopsis = synopsis;
    }

    public String getIsbn() {
        return this.isbn;
    }

    public String getMovieName() {
        return this.movieName;
    }

    public String getSynopsis() {
        return this.synopsis;
    }

    @Override
    public String toString() {
        return isbn + " ----- " + synopsis;
    }
}
