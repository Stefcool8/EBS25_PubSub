package org.pub_sub.broker.notify;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class SubscriberNotifier {
    public static void notify(String subscriberId, String message) throws IOException {
        HttpURLConnection conn = null;

        try {
            URL url = new URL("http://" + subscriberId + "/notify");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = message.getBytes(StandardCharsets.UTF_8);
                os.write(input);
                os.flush();
            }

            int status = conn.getResponseCode();
            String response;
            try (InputStream is = status >= 200 && status < 300
                    ? conn.getInputStream()
                    : conn.getErrorStream()) {
                response = new BufferedReader(new InputStreamReader(is))
                        .lines()
                        .reduce("", (acc, line) -> acc + line + "\n");
            }

            System.out.printf("Notified %s (status %d): %s%n", subscriberId, status, response);

        } catch (IOException e) {
            System.err.printf("Failed to notify subscriber %s: %s%n", subscriberId, e.getMessage());
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }
}
