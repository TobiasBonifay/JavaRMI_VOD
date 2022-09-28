package fr.polytech.rmi.server;

import java.util.ArrayList;
import java.util.List;

public class VODService implements IVODService {
    private List<MovieDesc> viewCatalog() {
        return new ArrayList<MovieDesc>();
    }

    Bill playmovie(String isbn, IClientBox box) {
        return new Bill(isbn, box);
    }

}
