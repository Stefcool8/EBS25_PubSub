package org.pub_sub.broker;

import org.pub_sub.broker.topology.BrokerTopology;

public class Main {
    public static void main(String[] args) throws Exception {
        String brokerId = "1";
        String[] neighbors = new String[] {};

        if (args.length == 1) {
            brokerId = args[0];
        }
        else if (args.length > 1) {
            brokerId = args[0];
            neighbors = new String[args.length - 1];
            System.arraycopy(args, 1, neighbors, 0, args.length - 1);
        }

        new BrokerTopology().run(brokerId, neighbors);
    }
}