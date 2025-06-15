package org.pub_sub.broker.bolt;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.pub_sub.common.generated.SubscriptionProto;

import java.util.Map;

public class SimpleSubscriptionBolt extends BaseRichBolt {
    private OutputCollector collector;

    @Override
    public void prepare(Map<String, Object> map, TopologyContext topologyContext, OutputCollector outputCollector) {
        this.collector = outputCollector;
    }

    @Override
    public void execute(Tuple tuple) {
        SubscriptionProto.Subscription sub = (SubscriptionProto.Subscription) tuple.getValueByField("subscription");

        System.out.println("Received subscription: " + sub.toString());
        
        // Store the subscription
        SubscriptionManager.addSubscription(sub);

        collector.emit(new Values(sub));
        collector.ack(tuple);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new org.apache.storm.tuple.Fields("subscription"));
    }
}
