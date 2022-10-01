package fr.polytech.rmi.server;

import java.rmi.RemoteException;

public non-sealed class MovieDescExtended extends MovieDesc {
    protected final String synopsis;
    protected final Byte[] teaser;


    /**
     * Synopsis of the movie is the same in both class
     *
     * @param isbn
     * @param movieName
     * @param synopsis
     * @param teaser
     */
    public MovieDescExtended(String isbn, String movieName, String synopsis, Byte[] teaser) throws RemoteException {
        super(isbn, movieName, synopsis);
        this.synopsis = synopsis;
        this.teaser = teaser;
    }

    @Override
    public String getSynopsis() {
        return this.synopsis;
    }

    public Byte[] getTeaser() {
        return this.teaser;
    }
}
