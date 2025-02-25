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

/**
 * VODService
 *
 * @author Tobias Bonifay
 * @author Ihor Melnyk
 */
public class VODService extends UnicastRemoteObject implements IVODService, Serializable {

    private static final Logger LOGGER = Logger.getLogger(VODService.class.getName());
    private static final String MOVIES_TXT = "movies.txt";

    private final List<MovieDesc> movieDescList;

    protected VODService() throws RemoteException {
        super();
        movieDescList = new ArrayList<>();
        readMoviesData();
    }

    @Override
    public List<MovieDesc> viewCatalog() throws RemoteException {
        return this.movieDescList;
    }

    @Override
    public Bill playMovie(String isbn, IClientBox box) throws RemoteException {
        try {
            byte[] data = flow(isbn);
            int i = 0;
            while (i < data.length){
                box.stream(new byte[]{data[i]});
                i++;
                if ( (i % 6) == 0){
                    Thread.sleep(16);
                }
            }
        } catch (IOException | InterruptedException e) {
            LOGGER.severe("Can't play the movie! Try again.");
        }
        String filmName = null;
        for (MovieDesc md : movieDescList){
            if (md.getIsbn().equals(isbn)) filmName = md.getMovieName();
        }
        return new Bill(filmName, BigInteger.valueOf(123456789123L));
    }


    /**
     * Remote exception is a subclass of IOException
     *
     * @return byte array of a file
     * @throws RemoteException
     * @throws FileNotFoundException
     * @throws IOException
     */
    private byte[] flow(String isbn) throws IOException {
        // name should be given to the method

        // will be filled in parameter
        final Path path = Paths.get("src/fr/polytech/rmi/server/videos/" + isbn + ".mp4");
        final File file = new File(path.toUri());

        LOGGER.info("Looking for file " + file.getAbsolutePath());

        final int bufferSize = file.length() > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) file.length();
        byte[] bytes = new byte[bufferSize];

        LOGGER.info("Generating stream...");
        try (InputStream is = new FileInputStream(file)) {
            if (is.read(bytes, 0, bufferSize) == -1) LOGGER.info("end of file, reached");
            return bytes;         // return the name with Pair if you can't find any other way of doing it
        } catch (FileNotFoundException e) {
            LOGGER.severe("File " + file.getAbsolutePath() + " not found\n" + e);
        } finally {
            LOGGER.info("Done reading " + file.getAbsolutePath());
        }
        return new byte[0];
    }

    /**
     * Fill the class attribut movieDescList with MovieDesc using a file.
     */
    private void readMoviesData() {
        try {
            // Le fichier d'entrée
            final FileInputStream file = new FileInputStream(MOVIES_TXT);
            final Scanner scanner = new Scanner(file);
            String isbn;
            String movieName;
            String synopsys;

            while (scanner.hasNextLine()) {
                isbn = scanner.nextLine();
                movieName = scanner.nextLine();
                synopsys = scanner.nextLine();
                movieDescList.add(new MovieDesc(isbn, movieName, synopsys));
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
