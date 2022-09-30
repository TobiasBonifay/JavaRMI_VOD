package fr.polytech.rmi.server;

import java.io.Serializable;
import java.math.BigInteger;

public class Bill implements Serializable {
    private final String movieName;
    private final BigInteger outrageousPrice;


    public Bill(String movieName, BigInteger outrageousPrice) {
        this.movieName = movieName;
        this.outrageousPrice = outrageousPrice;
    }

    public String getMovieName() {
        return movieName;
    }

    public BigInteger getOutrageousPrice() {
        return outrageousPrice;
    }

    @Override
    public String toString() {
        return "Bill : " +
                "movie : " + movieName +
                ", Price : " + outrageousPrice + "$" ;
    }
}
