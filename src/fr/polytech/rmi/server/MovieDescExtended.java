package fr.polytech.rmi.server;

import java.io.Serializable;

final class MovieDescExtended extends MovieDesc implements Serializable {
    private final String synopsis;
    private final byte[] teaser;


    /**
     * Synopsis of the movie is the same in both class
     *
     * @param isbn
     * @param movieName
     * @param synopsis
     * @param teaser
     */
    public MovieDescExtended(String isbn, String movieName, String synopsis, byte[] teaser) {
        super(isbn, movieName, synopsis);
        this.synopsis = synopsis;
        this.teaser = teaser;
    }

    @Override
    public String getSynopsis() {
        return this.synopsis;
    }

    public byte[] getTeaser() {
        return this.teaser;
    }
}
