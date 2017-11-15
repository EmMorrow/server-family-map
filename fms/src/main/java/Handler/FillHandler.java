package Handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

import Request.FillRequest;
import Result.FillResult;
import Service.FillService;

/**
 * Created by emilychandler on 10/24/17.
 */

public class FillHandler implements HttpHandler{
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

                String uri = exchange.getRequestURI().toString();
                String[] arr = uri.split("/");
                FillRequest request = null;
                if (arr.length == 3) {
                    request = new FillRequest(arr[2]);
                }
                else if (arr.length == 4) {
                    request = new FillRequest(arr[2],Integer.parseInt(arr[3]));
                }

                FillService service = new FillService();
                FillResult result = service.fill(request);
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
