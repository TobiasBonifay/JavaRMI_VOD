package fr.polytech.rmi.server;

import fr.polytech.rmi.client.IClientBox;

import java.io.Serializable;
import java.math.BigInteger;
import java.rmi.RemoteException;

public class Bill implements Serializable {
    private String movieName;
    private BigInteger outrageousPrice;

    public Bill(String isbn, IClientBox box) throws RemoteException {

    }

    public String getMovieName() throws RemoteException {
        return movieName;
    }

    public BigInteger getOutrageousPrice() throws RemoteException {
        return outrageousPrice;
    }
}
