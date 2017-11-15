package Handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import org.apache.commons.io.*;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;


/**
 * Created by emilychandler on 10/29/17.
 */

public class DefaultHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException
    {
        OutputStream respBody = null;
        try{
            URI rp = exchange.getRequestURI();
            String relativePath = rp.toString();
            if (relativePath.equals("/")) relativePath = "index.html";

            System.out.println("    Path received: " + relativePath);

            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

            respBody = exchange.getResponseBody();

            String file = "fms" + File.separator + "web" + File.separator + relativePath;
            byte[] fileData = null;
            try {
                fileData = FileUtils.readFileToByteArray(new File(file));
            } catch (IOException e) {
                file = "fms" + File.separator + "web" + File.separator + "HTML" + File.separator + "404.html";
                fileData = FileUtils.readFileToByteArray(new File(file));
            }

            respBody.write(fileData);
        }
        catch(Exception e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
        }

        respBody.close();

    }

}
