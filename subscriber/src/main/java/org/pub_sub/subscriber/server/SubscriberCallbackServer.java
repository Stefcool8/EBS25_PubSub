package org.pub_sub.subscriber.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.pub_sub.common.records.PubRecord;

import java.util.Date;

import static spark.Spark.port;
import static spark.Spark.post;

public class SubscriberCallbackServer {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static void start(int port) {
        port(port);

        post("/notify", (request, response) -> {
            PubRecord pub = MAPPER.readValue(request.body(), PubRecord.class);
            System.out.println("Received notification from broker:\n" + pub);
            response.status(200);

            long receivedTime = new Date().getTime();

            try {
                java.nio.file.Files.writeString(
                        java.nio.file.Paths.get("evaluator/src/main/java/org/pub_sub/notifications-" + port + ".txt"),
                        pub.timestamp + ", " + receivedTime + "\n",
                        java.nio.charset.StandardCharsets.UTF_8,
                        java.nio.file.StandardOpenOption.CREATE,
                        java.nio.file.StandardOpenOption.APPEND
                );
            } catch (java.io.IOException e) {
                System.err.println("Error writing to notifications.txt: " + e.getMessage());
            }

            return "Received";
        });
    }
}
