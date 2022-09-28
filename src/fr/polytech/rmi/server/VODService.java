package fr.polytech.rmi.server;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class VODService implements IVODService
{
    private BigInteger outrageousPrice;
    private List<MovieDesc> viewCatalog() {
        return new ArrayList<MovieDesc>();
    }

}
