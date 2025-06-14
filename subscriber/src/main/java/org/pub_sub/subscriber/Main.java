package org.pub_sub.subscriber;

import org.pub_sub.subscriber.server.SubscriberCallbackServer;
import org.pub_sub.subscriber.topology.SubscriberTopology;

public class Main
{
    public static void main( String[] args ) throws Exception {
        SubscriberCallbackServer.start();
        SubscriberTopology.run();
    }
}
