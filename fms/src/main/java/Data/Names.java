package Data;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by emilychandler on 10/25/17.
 */

public class Names {
    ArrayList<String> data;

    public Names() {
        data = new ArrayList<String>();
    }

    public Names(ArrayList<String> data) {
        this.data = data;
    }

    public String getRandomName() {
        Random rand = new Random();
        Double n = Math.random() * data.size();

        return data.get(n.intValue());
    }

    public ArrayList<String> getData() {
        return data;
    }

    public void setData(ArrayList<String> data) {
        this.data = data;
    }
}
