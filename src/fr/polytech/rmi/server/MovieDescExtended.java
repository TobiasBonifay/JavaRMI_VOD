package fr.polytech.rmi.server;

public class MovieDescExtended extends MovieDesc {
    protected String synopsis;
    protected Byte[] teaser;


    /**
     * Synopsis of the movie is the same in both class
     *
     * @param isbn
     * @param movieName
     * @param synopsis
     * @param teaser
     */
    public MovieDescExtended(String isbn, String movieName, String synopsis, Byte[] teaser) {
        super(isbn, movieName, synopsis);
        this.synopsis = synopsis;
        this.teaser = teaser;
    }
}
