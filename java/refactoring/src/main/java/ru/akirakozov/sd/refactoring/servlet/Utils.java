package ru.akirakozov.sd.refactoring.servlet;
import ru.akirakozov.sd.refactoring.SqlResult;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class Utils {
    public static String getResponse(InputStream stream) {
        StringBuilder text = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                text.append(line);
            }
        } catch (IOException e) {
            System.err.println("Can't read response from stream. Cause: " + e.getMessage());
        }
        return text.toString();
    }

    public static InputStream sendRequest(String request) {
        InputStream stream = null;
        try {
            stream = new URL(request).openStream();
        } catch (IOException e) {
            System.err.println("Can't send request. Cause: " + e.getMessage());
        }
        return stream;
    }

    public static String generateHtml(SqlResult result) {
        return "<html><body>\n" +
                result.data() +
                "</body></html>";
    }
}
