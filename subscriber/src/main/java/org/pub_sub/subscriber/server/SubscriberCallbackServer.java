package org.pub_sub.subscriber.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.pub_sub.common.records.PubRecord;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Date;

import static spark.Spark.port;
import static spark.Spark.post;

public class SubscriberCallbackServer {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static void start(int port) {
        port(port);

        Path filePath = Paths.get("evaluator/src/main/java/org/pub_sub/notifications-" + port + ".txt");

        // ensure file is cleared on startup
        try {
            if (Files.exists(filePath)) {
                Files.delete(filePath);
                System.out.println("Cleared existing file: " + filePath);
            }
        } catch (IOException e) {
            System.err.println("Error clearing existing notification file " + filePath + ": " + e.getMessage());
            return;
        }

        post("/notify", (request, response) -> {
            PubRecord pub = MAPPER.readValue(request.body(), PubRecord.class);
            System.out.println("Received notification from broker:\n" + pub);
            response.status(200);

            long receivedTime = new Date().getTime();

            try {
                Files.writeString(
                        filePath,
                        pub.timestamp + ", " + receivedTime + "\n",
                        StandardCharsets.UTF_8,
                        StandardOpenOption.CREATE,
                        StandardOpenOption.APPEND
                );
            } catch (java.io.IOException e) {
                System.err.println("Error writing to notifications.txt: " + e.getMessage());
            }

            return "Received";
        });
    }
}
