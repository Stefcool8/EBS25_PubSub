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
            return "Received";
        });
    }
}
