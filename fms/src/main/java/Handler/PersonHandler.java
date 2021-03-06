package Handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

import Dao.Database;
import Dao.DatabaseException;
import Model.AuthToken;
import Model.Person;
import Result.PersonResult;
import Service.PersonService;

/**
 * Created by emilychandler on 10/24/17.
 */

public class PersonHandler implements HttpHandler{
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;
        ReaderWriter rw = new ReaderWriter();
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("get")) {
                Headers reqHeaders = exchange.getRequestHeaders();

                if (reqHeaders.containsKey("Authorization")) {
                    String authToken = reqHeaders.getFirst("Authorization");
                    Database db = new Database();
                    try {
                        String respData = null;
                        db.openConnection();
                        AuthToken myAuthToken = db.getAuthDao().getAuthToken(authToken);
                        db.closeConnection(true);

                        if (myAuthToken != null) {
                            PersonService service = new PersonService();
                            Gson gson = new Gson();
                            String uri = exchange.getRequestURI().toString();
                            String[] arr = uri.split("/");

                            if (arr.length == 2) {
                                PersonResult result = service.getPeople(authToken);
                                respData = gson.toJson(result);
                            } else if (arr.length == 3) {
                                Person result = service.getPerson(arr[2], myAuthToken.getUsername());

                                respData = gson.toJson(result);
                            }
                            success = true;
                        }
                        else {
                            Gson gson = new Gson();
                            Person result = new Person();
                            result.setPersonIdNull();
                            result.setMessage("AuthToken invalid");
                            respData = gson.toJson(result);
                        }
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                        OutputStream respBody = exchange.getResponseBody();
                        rw.writeString(respData, respBody);
                        respBody.close();
                    }
                    catch (DatabaseException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (!success) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
            }
        }
        catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
}
