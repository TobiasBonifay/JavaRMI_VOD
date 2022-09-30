package fr.polytech.rmi.server;

import fr.polytech.rmi.client.IClientBox;
import fr.polytech.rmi.server.interfaces.IVODService;

import java.io.*;
import java.math.BigInteger;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class VODService extends UnicastRemoteObject implements IVODService, Serializable {

    private final List<MovieDesc> movieDescList;

    private static final Logger LOGGER = Logger.getLogger(VODService.class.getName());

    protected VODService() throws RemoteException {
        super();
        movieDescList = new ArrayList<>();
        readMoviesData();
        }

    @Override
    public List<MovieDesc> viewCatalog() throws RemoteException {
        return this.movieDescList;
    }

    public Bill playMovie(String isbn, IClientBox box) throws RemoteException {
        try {
            box.stream(flow(isbn));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new Bill("Harry Potter", new BigInteger("123456789123"));
    }

    @Override
    public void echo() throws RemoteException {
        IVODService.super.echo();
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
    public byte[] flow(String isbn) throws RemoteException, IOException {
        // name should be given to the method

        // will be filled in parameter
        final Path path = Paths.get("src/fr/polytech/rmi/server/videos/" + isbn + ".mp4");
        final File file = new File(path.toUri());

        LOGGER.info("Looking for file " + file.getAbsolutePath());

        final int bufferSize = file.length() > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) file.length();
        byte[] bytes = new byte[bufferSize];

        LOGGER.info("Generating stream...");
        InputStream is = null;
        try {
            is = new FileInputStream(file);
            is.read(bytes, 0, bufferSize);
            return bytes;         // return the name with Pair if you can't find any other way of doing it
        } catch (FileNotFoundException e) {
            LOGGER.severe("File " + file.getAbsolutePath() + " not found\n" + e);
        } finally {
            is.close();
            LOGGER.info("Done reading " + file.getAbsolutePath());
        }
        return new byte[0];
    }

    void readMoviesData(){
        try
        {
            // Le fichier d'entrée
            FileInputStream file = new FileInputStream("movies.txt");
            Scanner scanner = new Scanner(file);
            String isbn;
            String movieName;
            String synopsys;

            while(scanner.hasNextLine())
            {
                isbn = scanner.nextLine();
                movieName = scanner.nextLine();
                synopsys = scanner.nextLine();
                movieDescList.add(new MovieDesc (isbn,movieName,synopsys));
            }
            scanner.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
