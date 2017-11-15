package Service;

import java.sql.SQLException;

import Dao.Database;
import Dao.DatabaseException;
import Model.AuthToken;
import Model.User;
import Result.LoginResult;
import Request.LoginRequest;
/**
 * Created by emilychandler on 10/11/17.
 */

public class LoginService {
    private Database db;

    public LoginService() {
        db = new Database();
    }
    /**
     * Logs in the user
     * @param request Contains information of the user to be logged in
     * @return the result of the login
     */
    public LoginResult login(LoginRequest request) {
        LoginResult result = new LoginResult();
        try {
            if (request.getUsername() == null || request.getPassword() == null) {
                result.setMessage("Request property missing or has invalid value");
                return result;
            }

            db.openConnection();
            User user = db.getUserDao().getUser(request.getUsername());
            if (user == null) {
                result.setMessage("User does not exist");
                db.closeConnection(false);
                return result;
            }

            if (user.getPassword().equals(request.getPassword())) {
                AuthToken auth = new AuthToken(request.getUsername());
                db.getAuthDao().createAuthToken(auth);

                result.setPersonId(user.getPersonId());
                result.setUsername(request.getUsername());
                result.setAuthToken(auth.getAuthToken());
            } else {
                result.setMessage("Incorrect Password");
            }
            db.closeConnection(true);
        }
        catch(DatabaseException e) {
            e.printStackTrace();
        }
        return result;
    }
}
