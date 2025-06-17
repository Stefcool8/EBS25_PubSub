package org.pub_sub.publisher.topology;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
import org.pub_sub.publisher.bolt.KafkaPublisherBolt;
import org.pub_sub.publisher.spout.PublicationsGeneratorSpout;

public class PublisherTopology {
    public static void run(String brokerId) throws Exception {
        Configurator.setLevel("org.apache.storm",     Level.WARN);
        Configurator.setLevel("org.apache.zookeeper", Level.ERROR);
        Configurator.setLevel("io.netty",             Level.ERROR);
        Configurator.setLevel("org.apache.kafka",     Level.WARN);
        Configurator.setLevel("kafka",                Level.WARN);

        String topic = "broker-" + brokerId + "-forward";

        TopologyBuilder builder = new TopologyBuilder();

        builder.setSpout("publications-spout", new PublicationsGeneratorSpout());
        builder.setBolt("kafka-publisher", new KafkaPublisherBolt(topic)).shuffleGrouping("publications-spout");

        Config config = new Config();
        config.setDebug(false);
        config.put("pub.total", 1000000);
        config.put("pub.threads", 4);

        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("publisher-topology", config, builder.createTopology());
    }
}
