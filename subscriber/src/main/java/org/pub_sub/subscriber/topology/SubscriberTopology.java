package org.pub_sub.subscriber.topology;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
import org.pub_sub.subscriber.bolt.KafkaSubscriberBolt;
import org.pub_sub.subscriber.spout.SubscriptionsGeneratorSpout;

public class SubscriberTopology {
    public static void run(String brokerId, String port) throws Exception {
        String topic = "broker-" + brokerId + "-admin";
        String subscriberId = "localhost:" + port;

        TopologyBuilder builder = new TopologyBuilder();

        builder.setSpout("subscriptions-spout", new SubscriptionsGeneratorSpout(subscriberId));
        builder.setBolt("kafka-subscriber", new KafkaSubscriberBolt(topic, subscriberId))
                .shuffleGrouping("subscriptions-spout");

        Config config = new Config();
        config.setDebug(false);

        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("subscriber-topology", config, builder.createTopology());
    }
}
