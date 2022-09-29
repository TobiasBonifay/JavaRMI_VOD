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
import java.util.logging.Logger;

public class VODService extends UnicastRemoteObject implements IVODService, Serializable {

    private final List<MovieDesc> movieDescList;

    private static final Logger LOGGER = Logger.getLogger(VODService.class.getName());

    protected VODService() throws RemoteException {
        super();
        movieDescList = new ArrayList<>();

        //hardcoded movies for test
        movieDescList.add(new MovieDesc("9485734573847", "Harry Potter", "" +
                "Harry Potter is a film series based on the eponymous novels by J. K. Rowling. The series is produced and distributed by Warner Bros. Pictures and consists of eight fantasy films, beginning with Harry Potter and the Philosopher's Stone (2001) and culminating with Harry Potter and the Deathly Hallows – Part 2 (2011).[2][3] A spin-off prequel series, planned to consist of five films, started with Fantastic Beasts and Where to Find Them (2016), marking the beginning of the Wizarding World shared media franchise."));
    }
    @Override
    public List<MovieDesc> viewCatalog() throws RemoteException {
        return this.movieDescList;
    }

    Bill playMovie(String isbn, IClientBox box) throws RemoteException {
        return new Bill(isbn, box);
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
        final Path path = Paths.get("src/fr/polytech/rmi/server/videos/" + isbn);
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
}
