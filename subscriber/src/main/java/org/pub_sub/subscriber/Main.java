package org.pub_sub.subscriber;

import org.pub_sub.subscriber.server.SubscriberCallbackServer;
import org.pub_sub.subscriber.topology.SubscriberTopology;

public class Main
{
    public static void main( String[] args ) throws Exception {
        String brokerId = "1";
        String port = "8082";

        if (args.length == 2) {
            brokerId = args[0];
            port = args[1];
        }

        SubscriberCallbackServer.start(Integer.parseInt(port));
        SubscriberTopology.run(brokerId, port);
    }
}
