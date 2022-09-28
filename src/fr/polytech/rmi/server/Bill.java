package fr.polytech.rmi.server;

import java.math.BigInteger;

public class Bill {
    private String movieName;
    private BigInteger outrageousPrice;

    public String getMovieName() {
        return movieName;
    }

    public BigInteger getOutrageousPrice() {
        return outrageousPrice;
    }
}
