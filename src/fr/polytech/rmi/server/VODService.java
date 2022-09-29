package fr.polytech.rmi.server;

import fr.polytech.rmi.client.IClientBox;
import fr.polytech.rmi.server.interfaces.IVODService;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        return this;
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
        final Path path = Paths.get("src/fr/polytech/rmi/server/videos/example.mp4");
        final File file = new File(path.toUri());

        System.out.println("Looking for file " + file.getAbsolutePath());

        final int bufferSize = file.length() > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) file.length();
        byte[] bytes = new byte[bufferSize];

        System.out.println("Generating stream...");
        InputStream is = null;
        try {
            is = new FileInputStream(file);
            is.read(bytes, 0, bufferSize);
            return bytes;         // return the name with Pair if you can't find any other way of doing it
        } catch (FileNotFoundException e) {
            System.err.println("File not found...");
            e.printStackTrace();
        } finally {
            is.close();
            System.out.println("Done.");
        }

        return null;
    }
}
