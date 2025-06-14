package org.pub_sub.broker.bolt;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.pub_sub.broker.notify.SubscriberNotifier;
import org.pub_sub.common.generated.PublicationProto;

import java.io.IOException;
import java.util.Map;

public class SimplePublicationBolt extends BaseRichBolt {

    private OutputCollector collector;

    @Override
    public void prepare(Map<String, Object> map, TopologyContext topologyContext, OutputCollector outputCollector) {
        this.collector = outputCollector;
    }

    @Override
    public void execute(Tuple tuple) {
        PublicationProto.Publication pub = (PublicationProto.Publication) tuple.getValueByField("publication");

        System.out.println("Received publication: " + pub.toString());
        try {
            SubscriberNotifier.notify("localhost:8082", pub.toString());
        } catch (IOException e) {
            System.err.println("Error notifying subscribers: " + e.getMessage());
            e.printStackTrace();
        }

        collector.emit(new Values(pub));

        collector.ack(tuple);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new org.apache.storm.tuple.Fields("publication"));
    }
}
