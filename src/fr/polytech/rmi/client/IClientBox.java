package fr.polytech.rmi.client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IClientBox extends Remote {

    void stream(byte[] chunk) throws RemoteException;

}
