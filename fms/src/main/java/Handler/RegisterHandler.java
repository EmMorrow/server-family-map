package Handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.org.apache.xml.internal.utils.StringBufferPool;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

import Request.RegisterRequest;
import Service.RegisterService;
import Result.RegisterResult;

/**
 * Created by emilychandler on 10/24/17.
 */

public class RegisterHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;
        ReaderWriter rw = new ReaderWriter();
        try {
            if(exchange.getRequestMethod().toLowerCase().equals("post")) {
                InputStream reqBody = exchange.getRequestBody();
                String reqData = rw.readString(reqBody);
                System.out.println(reqData);

                Gson gson = new Gson();
                RegisterRequest request = gson.fromJson(reqData, RegisterRequest.class);

                RegisterService service = new RegisterService();
                RegisterResult result = service.register(request);
                String respData = gson.toJson(result);

                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                OutputStream respBody = exchange.getResponseBody();
                rw.writeString(respData, respBody);

                respBody.close();

                success = true;
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
