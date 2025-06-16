package org.pub_sub.broker.topology;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
import org.pub_sub.broker.bolt.ForwardMessageBolt;
import org.pub_sub.broker.bolt.AdminMessageBolt;
import org.pub_sub.broker.spout.KafkaForwardMessagesSpout;
import org.pub_sub.broker.spout.KafkaAdminMessagesSpout;


public class BrokerTopology {
    public static void run(String brokerId, String[] neighbors) throws Exception {
        TopologyBuilder builder = new TopologyBuilder();

        String adminTopic = "broker-" + brokerId + "-admin";
        String forwardTopic = "broker-" + brokerId + "-forward";

        builder.setSpout("kafka-forward-spout", new KafkaForwardMessagesSpout(forwardTopic));
        builder.setSpout("kafka-admin-spout", new KafkaAdminMessagesSpout(adminTopic));

        builder.setBolt("forward-bolt", new ForwardMessageBolt(brokerId, neighbors))
                .shuffleGrouping("kafka-forward-spout");
        builder.setBolt("admin-bolt", new AdminMessageBolt(brokerId, neighbors))
                .shuffleGrouping("kafka-admin-spout");

        Config config = new Config();
        config.setDebug(false);

        try (LocalCluster cluster = new LocalCluster()) {
            cluster.submitTopology("broker-topology", config, builder.createTopology());
            Thread.sleep(180_000);
            cluster.shutdown();
        }
    }
}
