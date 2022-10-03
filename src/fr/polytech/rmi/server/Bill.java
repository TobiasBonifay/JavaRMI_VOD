package fr.polytech.rmi.server;

import java.io.Serializable;
import java.math.BigInteger;

public record Bill(String movieName, BigInteger outrageousPrice) implements Serializable {
    @Override
    public String toString() {
        return "Bill : \n" +
                "Movie: " + movieName +
                ", Price: " + outrageousPrice + "$";
    }
}
