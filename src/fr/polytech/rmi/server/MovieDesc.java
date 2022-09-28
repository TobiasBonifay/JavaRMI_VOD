package fr.polytech.rmi.server;

public class MovieDesc {
    protected String isbn;
    protected String movieName;
    protected String synopsis;

    public MovieDesc(String isbn, String movieName, String synopsis) {
        this.isbn = isbn;
        this.movieName = movieName;
        this.synopsis = synopsis;
    }
}
