package org.pub_sub.publisher;

import org.pub_sub.publisher.topology.PublisherTopology;

public class Main
{
    public static void main( String[] args ) throws Exception {
        String brokerId = "1";

        if (args.length == 1) {
            brokerId = args[0];
        }
        PublisherTopology.run(brokerId);
    }
}
