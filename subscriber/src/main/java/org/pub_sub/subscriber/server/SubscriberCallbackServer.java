package org.pub_sub.subscriber.server;

import static spark.Spark.port;
import static spark.Spark.post;

public class SubscriberCallbackServer {
    public static void start() {
        port(8082);

        post("/notify", (request, response) -> {
            String body = request.body();
            System.out.println("📩 Received notification from broker:\n" + body);
            response.status(200);

            try {
                java.nio.file.Files.writeString(
                        java.nio.file.Paths.get("notifications.txt"),
                        body + "\n",
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
