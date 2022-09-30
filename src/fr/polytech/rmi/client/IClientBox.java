package fr.polytech.rmi.client;

import java.rmi.Remote;

public interface IClientBox extends Remote {

    void stream(byte[] chunk);

}
