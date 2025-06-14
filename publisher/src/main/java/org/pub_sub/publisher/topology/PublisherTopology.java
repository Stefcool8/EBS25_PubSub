package org.pub_sub.publisher.topology;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
import org.pub_sub.publisher.bolt.KafkaPublisherBolt;
import org.pub_sub.publisher.spout.PublicationsGeneratorSpout;

public class PublisherTopology {
    public static void run() throws Exception {
        String brokerId = System.getenv().getOrDefault("BROKER_ID", "1");
        String topic = "pub-to-broker-" + brokerId;

        TopologyBuilder builder = new TopologyBuilder();

        builder.setSpout("publications-spout", new PublicationsGeneratorSpout());
        builder.setBolt("kafka-publisher", new KafkaPublisherBolt(topic)).shuffleGrouping("publications-spout");

        Config config = new Config();
        config.setDebug(false);

        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("publisher-topology", config, builder.createTopology());
    }
}
