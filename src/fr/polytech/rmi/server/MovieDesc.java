package fr.polytech.rmi.server;

import java.io.Serializable;

/**
 * MovieDesc
 *
 * @author Tobias Bonifay
 * @author Ihor Melnyk
 */
public sealed class MovieDesc implements Serializable permits MovieDescExtended {
    protected final String isbn;
    protected final String movieName;
    protected final String synopsis;

    public MovieDesc(String isbn, String movieName, String synopsis) {
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
