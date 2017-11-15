package Result;

import java.util.ArrayList;

import Model.Person;

/**
 * Created by emilychandler on 10/24/17.
 */

public class PersonResult {
    private ArrayList<Person> data;
    private String message;

    public PersonResult() {
        data = null;
        message = null;
    }

    public ArrayList<Person> getData() {
        return data;
    }

    public void setData(ArrayList<Person> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
