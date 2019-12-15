package de.failender.heldensoftware.api;

import de.failender.heldensoftware.api.requests.ApiRequest;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Collections;
import java.util.Map;

public class RestUtils {


    public static InputStream request(String uri, String body, String method) {
        return request(uri, body, method, Collections.EMPTY_MAP);
    }

    public static InputStream request(String uri, String body, String method, Map<String, String> headers) {
        URL url = null;
        try {
            url = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");

            if(body != null) {
                connection.setRequestProperty("Content-Length", String.valueOf(body.length()));
            }
            headers.entrySet().forEach(header -> {
                connection.setRequestProperty(header.getKey(), header.getValue());
            });
            if(body != null) {
                OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                writer.write(body);
                writer.close();

            }
            return connection.getInputStream();
        } catch (Exception e) {
            logError(uri, body);
            throw new RuntimeException(e);

        }

    }

    private static void logError(String url, String body) {
        System.out.println("############");
        System.out.println("Received failed request to url " +  url);
        System.out.println("Body is: ");
        System.out.println(body);
        System.out.println("############");
    }
}
