package Service;

import java.sql.SQLException;

import Dao.Database;
import Dao.DatabaseException;
import Model.User;
import Request.FillRequest;
import Request.LoginRequest;
import Result.LoginResult;
import Result.RegisterResult;
import Request.RegisterRequest;

public class RegisterService {
    private Database db;

    public RegisterService() {
        db = new Database();
    }
    /**
     * Creates a new user account
     * @param r the request information
     * @return the result
     */
    public RegisterResult register(RegisterRequest r) {
        RegisterResult result = new RegisterResult();
        try {
            db.openConnection();
            if (db.getUserDao().getUser(r.getUsername()) == null) {
                User myuser = new User();

                myuser.setUsername(r.getUsername());
                myuser.setPassword(r.getPassword());
                myuser.setEmail(r.getEmail());
                myuser.setFirstName(r.getFirstName());
                myuser.setLastName(r.getLastName());
                myuser.setGender(r.getGender());

                if (myuser.getUsername() == null || myuser.getPassword() == null || myuser.getEmail() == null
                        || myuser.getFirstName() == null || myuser.getLastName() == null || myuser.getGender() == null) {
                    result.setMessage("Request property missing or has invalid value");
                    db.closeConnection(false);
                    return result;
                }

                db.getUserDao().createUser(myuser);
                db.closeConnection(true);
                generateData(r.getUsername());

                String authToken = login(r.getUsername(), r.getPassword());

                result.setAuthToken(authToken);
                result.setUserName(r.getUsername());
                result.setPersonID(myuser.getPersonId());
            } else {
                result.setMessage("Username already taken by another user");
                db.closeConnection(true);
            }

        }
        catch (DatabaseException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Calls fill to generate 4 generations of data
     * for the user
     */
    public String generateData(String username) {
        String personId = null;
        FillService service = new FillService();
        FillRequest request = new FillRequest(username);

        service.fill(request);
        personId = service.getRootPersonId();
        return personId;
    }

    /**
     * Calls login to login the new user
     */
    public String login(String username, String password) {
        LoginService service = new LoginService();
        LoginRequest request = new LoginRequest();
        request.setUserName(username);
        request.setPassword(password);

        LoginResult result = service.login(request);

        return result.getAuthToken();
    }

}
