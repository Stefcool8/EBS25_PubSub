package org.pub_sub.subscriber;

import org.pub_sub.subscriber.server.SubscriberCallbackServer;
import org.pub_sub.subscriber.topology.SubscriberTopology;

public class Main
{
    public static void main( String[] args ) throws Exception {
        String brokerId = "1";

        if (args.length == 1) {
            brokerId = args[0];
        }

        SubscriberCallbackServer.start();
        SubscriberTopology.run(brokerId);
    }
}
