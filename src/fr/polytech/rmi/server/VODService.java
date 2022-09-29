package fr.polytech.rmi.server;

import fr.polytech.rmi.client.IClientBox;
import fr.polytech.rmi.server.interfaces.IVODService;

import java.io.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class VODService extends UnicastRemoteObject implements IVODService, Serializable {

    protected VODService() throws RemoteException {
        super();
    }

    private List<MovieDesc> viewCatalog() throws RemoteException {
        return new ArrayList<>();
    }

    Bill playMovie(String isbn, IClientBox box) throws RemoteException {
        return new Bill(isbn, box);
    }

    @Override
    public void echo() throws RemoteException {
        IVODService.super.echo();
    }

    @Override
    public IVODService getService() throws RemoteException {
        return null;
    }


    /**
     * I know IOException include FileNotFoundException... but let it for now
     *
     * @return byte array of a file
     * @throws RemoteException
     * @throws FileNotFoundException
     * @throws IOException
     */
    @Override
    public byte[] flow() throws RemoteException, IOException {

        // name should be given to the method

        // will be filled in parameter
        final String path = "empty";
        final File file = new File(path);

        final int bufferSize = file.length() > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) file.length();
        byte[] bytes = new byte[bufferSize];

        InputStream is = null;
        try {
            is = new FileInputStream(file);
            is.read(bytes, 0, bufferSize);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            is.close();
        }

        // return the name with Pair if can't find any other way of doing it
        return bytes;
    }
}
