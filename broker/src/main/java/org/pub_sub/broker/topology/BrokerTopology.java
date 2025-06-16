package org.pub_sub.broker.topology;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
import org.pub_sub.broker.bolt.SimplePublicationBolt;
import org.pub_sub.broker.bolt.SimpleSubscriptionBolt;
import org.pub_sub.broker.spout.KafkaPublicationSpout;
import org.pub_sub.broker.spout.KafkaSubscriptionSpout;

public class BrokerTopology {
    public static void run(String brokerId) throws Exception {
        TopologyBuilder builder = new TopologyBuilder();

        String adminTopic = "broker-" + brokerId + "-admin";
        String forwardTopic = "broker-" + brokerId + "-forward";

        builder.setSpout("kafka-publication-spout", new KafkaPublicationSpout(forwardTopic));
        builder.setSpout("kafka-subscription-spout", new KafkaSubscriptionSpout(adminTopic));

        builder.setBolt("simple-publication-bolt", new SimplePublicationBolt())
                .shuffleGrouping("kafka-publication-spout");
        builder.setBolt("simple-subscription-bolt", new SimpleSubscriptionBolt())
                .shuffleGrouping("kafka-subscription-spout");

        Config config = new Config();
        config.setDebug(false);

        try (LocalCluster cluster = new LocalCluster()) {
            cluster.submitTopology("broker-topology", config, builder.createTopology());
            Thread.sleep(180_000);
            cluster.shutdown();
        }
    }
}
