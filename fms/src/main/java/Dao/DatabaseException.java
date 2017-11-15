package Dao;

/**
 * Created by emilychandler on 10/25/17.
 */

public class DatabaseException extends Exception{
    public DatabaseException(String s) {
        super(s);
    }

    public DatabaseException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
