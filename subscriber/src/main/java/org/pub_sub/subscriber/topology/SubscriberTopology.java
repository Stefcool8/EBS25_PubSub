package org.pub_sub.subscriber.topology;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.thrift.TException;
import org.apache.storm.topology.TopologyBuilder;
import org.pub_sub.subscriber.bolt.KafkaSubscriberBolt;
import org.pub_sub.subscriber.spout.SubscriptionsGeneratorSpout;

public class SubscriberTopology {
    public static void run() throws Exception {
        String brokerId = System.getenv().getOrDefault("BROKER_ID", "1");
        String topic = "sub-to-broker-" + brokerId;

        TopologyBuilder builder = new TopologyBuilder();

        builder.setSpout("subscriptions-spout", new SubscriptionsGeneratorSpout());
        builder.setBolt("kafka-subscriber", new KafkaSubscriberBolt(topic)).shuffleGrouping("subscriptions-spout");

        Config config = new Config();
        config.setDebug(false);

        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("subscriber-topology", config, builder.createTopology());
    }
}
