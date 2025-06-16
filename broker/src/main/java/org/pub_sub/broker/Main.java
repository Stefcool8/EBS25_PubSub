package org.pub_sub.broker;

import org.pub_sub.broker.topology.BrokerTopology;

public class Main {
    public static void main(String[] args) throws Exception {
        String brokerId = "1";

        if (args.length == 1) {
            brokerId = args[0];
        }
        BrokerTopology.run(brokerId);
    }
}