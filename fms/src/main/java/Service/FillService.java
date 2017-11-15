package Service;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;

import Dao.Database;
import Dao.DatabaseException;
import Data.Location;
import Data.Locations;
import Data.Names;
import Model.Event;
import Model.Person;
import Model.User;
import Result.FillResult;
import Request.FillRequest;
/**
 * Created by emilychandler on 10/11/17.
 */

public class FillService {
    private Locations locations;
    private Names fNames;
    private Names mNames;
    private Names sNames;
    private ArrayList<Person> fathers;
    private ArrayList<Person> mothers;
    private int generations;
    private Database db;
    private String username;
    private String rootPersonId;

    public FillService() {
        db = new Database();
        personCount = 0;
        eventCount = 0;
        fathers = new ArrayList<Person>();
        mothers = new ArrayList<Person>();
    }

    /**
     * Generate data for ancestors of the user and put it in the database
     * @param r FillRequest object containing the username and generations
     * @return the object that results from the fill
     */
    public FillResult fill(FillRequest r) {
        try {
            generateData();
            generations = r.getGenerations();
            username = r.getUsername();

            if (generations < 0) return new FillResult("Generations is invalid");

            db.openConnection();
            User myUser = db.getUserDao().getUser(r.getUsername());
            if (db.getUserDao().getUser(username) != null) {
                db.getPersonDao().deletePerson(username);
                db.getEventDao().deleteEvent(username);
            }
            db.closeConnection(true);

            generateFamily(username, myUser.getLastName());
            addRootPerson(myUser);
        }
        catch (DatabaseException e) {
            e.printStackTrace();
        }
        String message = "Successfully added " + Integer.toString(personCount) +
                " persons and " + Integer.toString(eventCount) + " events to the database";

        return new FillResult(message);
    }

    private int personCount;

    private void addRootPerson(User myUser) {
        try {
            Person myPerson = new Person();

            myPerson.setPersonId(myUser.getPersonId());
            myPerson.setLastName(myUser.getLastName());
            myPerson.setFirstName(myUser.getFirstName());
            myPerson.setGender(myUser.getGender());
            myPerson.setDescendant(username);

            if (fathers.size() > 0) myPerson.setFather(fathers.get(0).getPersonId());
            if (mothers.size() > 0) myPerson.setMother(mothers.get(0).getPersonId());

            db.openConnection();
            db.getPersonDao().createPerson(myPerson);
            db.closeConnection(true);

            addEvent(myPerson, randomNumber(1992,1997), "birth");
        }
        catch (DatabaseException e) {
            e.printStackTrace();
        }
        personCount++;
    }

    private void generateFamily(String username, String lastName) {
        for (int i = generations; i > 0; i--) {
            double couples = (Math.pow(2, i))/2 ;
            for (double j = couples; j > 0; j--) {
                if (j == couples) addCouple(username,lastName,i);
                else addCouple(username,null,i);
            }
        }
    }

    private void addCouple(String username, String lastName, int generation) {
        Person fAncestor = new Person();
        Person mAncestor = new Person();
        String sName;

        if (lastName == null) sName = sNames.getRandomName();
        else sName = lastName;
        String fName = fNames.getRandomName();
        String mName = mNames.getRandomName();

        mAncestor.generatePersonId();
        mAncestor.setDescendant(username);
        mAncestor.setFirstName(mName);
        mAncestor.setLastName(sName);
        mAncestor.setGender("m");
        mAncestor.setSpouse(fAncestor.getPersonId());

        if (fathers.size() > 0 && generation < generations) {
            mAncestor.setFather(fathers.get(0).getPersonId());
            if (lastName == null) sName = fathers.get(0).getLastName();
            mAncestor.setLastName(sName);
            fathers.remove(0);
        }
        if (mothers.size() > 0 && generation < generations) {
            mAncestor.setMother(mothers.get(0).getPersonId());
            mothers.remove(0);
        }

        fAncestor.generatePersonId();
        fAncestor.setDescendant(username);
        fAncestor.setFirstName(fName);
        fAncestor.setLastName(sName);
        fAncestor.setGender("f");
        fAncestor.setSpouse(mAncestor.getPersonId());

        if (fathers.size() > 0 && generation < generations) {
            fAncestor.setFather(fathers.get(0).getPersonId());
            fathers.remove(0);
        }
        if (mothers.size() > 0 && generation < generations) {
            fAncestor.setMother(mothers.get(0).getPersonId());
            mothers.remove(0);
        }

        generateEvents(mAncestor,fAncestor,generation);

        try {
            db.openConnection();
            db.getPersonDao().createPerson(mAncestor);
            db.getPersonDao().createPerson(fAncestor);
            db.closeConnection(true);
        }
        catch (DatabaseException e) {
            e.printStackTrace();
        }
        personCount += 2;

        fathers.add(mAncestor);
        mothers.add(fAncestor);
    }

    private void generateEvents(Person male, Person female, int generations) {
        int fBirthYear = randomNumber(1992,1998) - (25 * generations);
        addEvent(female, fBirthYear, "birth");

        int mBirthYear = randomNumber(fBirthYear - 5, fBirthYear);
        addEvent(male, mBirthYear, "birth");

        int marriageYear = fBirthYear + randomNumber(20,27);
        addEvent(female, marriageYear, "marriage");
        addEvent(male, marriageYear, "marriage");

        if (2017 - fBirthYear > 70) {
            int fdeathDate = fBirthYear + randomNumber(70,2017 - fBirthYear);
            addEvent(female, fdeathDate, "death");
        }

        if (2017 - mBirthYear > 70) {
            int mdeathDate = mBirthYear + randomNumber(70,2017-mBirthYear);
            addEvent(male, mdeathDate, "death");
        }
    }

    private int eventCount;
    private Location marriageLocation;

    private void addEvent(Person person, int year, String eventType) {
       try {
           Event event = new Event();

           Location location = locations.getRandomLocation();
           if (person.getGender() == "m" && eventType == "marriage") location = marriageLocation;
           String date = Integer.toString(year);
           if (eventType == "marriage") marriageLocation = location;

           event.generateEventId();
           event.setDescendant(username);
           event.setPerson(person.getPersonId());
           event.setEventType(eventType);
           event.setYear(date);
           event.setLatitude(location.getLatitude());
           event.setLongitude(location.getLongitude());
           event.setCountry(location.getCountry());
           event.setCity(location.getCity());

           db.openConnection();
           db.getEventDao().createEvent(event);
           db.closeConnection(true);
           eventCount++;
       }
       catch (DatabaseException e) {
           e.printStackTrace();
       }
    }

    private void generateData() {
        try {
            Gson gson = new Gson();
            JsonReader reader = null;

            reader = new JsonReader(new FileReader("fms/json/fnames.json"));
            fNames = gson.fromJson(reader, Names.class);

            reader = new JsonReader(new FileReader("fms/json/mnames.json"));
            mNames = gson.fromJson(reader, Names.class);

            reader = new JsonReader(new FileReader("fms/json/snames.json"));
            sNames = gson.fromJson(reader, Names.class);

            reader = new JsonReader(new FileReader("fms/json/locations.json"));
            locations = gson.fromJson(reader, Locations.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Generates a random number between int low and int high
    private int randomNumber(int low, int high) {
        Random r = new Random();
        int result = r.nextInt(high - low) + low;
        return result;
    }

    public void setLocations(Locations locations) {
        this.locations = locations;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFNames(Names fNames) {
        this.fNames = fNames;
    }

    public void setMNames(Names mNames) {
        this.mNames = mNames;
    }

    public void setSNames(Names sNames) {
        this.sNames = sNames;
    }

    public String getRootPersonId() {
        return rootPersonId;
    }

    public int getEventCount() {
        return eventCount;
    }
}
