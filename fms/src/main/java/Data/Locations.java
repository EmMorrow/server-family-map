package Data;

import java.util.ArrayList;
import java.util.Random;

import Data.Location;

/**
 * Created by emilychandler on 10/25/17.
 */

public class Locations {
    private ArrayList<Location> data;

    public Locations() {
        data = new ArrayList<Location>();
    }

    public Locations(ArrayList<Location> data) {
        this.data = data;
    }

    public Location getRandomLocation() {
        Random rand = new Random();
        Double n = Math.random() * data.size();

        return data.get(n.intValue());
    }

    public ArrayList<Location> getData() {
        return data;
    }

    public void setData(ArrayList<Location> data) {
        this.data = data;
    }
}
