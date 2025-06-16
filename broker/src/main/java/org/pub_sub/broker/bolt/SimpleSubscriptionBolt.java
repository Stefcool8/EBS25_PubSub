package org.pub_sub.broker.bolt;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.pub_sub.broker.dtos.SubscriptionDto;
import org.pub_sub.common.generated.AdminProto;
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
        AdminProto.AdminMessage adminMessage = (AdminProto.AdminMessage) tuple.getValueByField("subscription");

        System.out.println("Received admin message: " + adminMessage.toString());
        
        // Procesăm doar mesajele de la subscriberi
        if (adminMessage.getSourceType() == AdminProto.SourceType.SUBSCRIBER) {
            // Procesăm abonările
            for (SubscriptionProto.Subscription subscription : adminMessage.getSubscriptionsList()) {
                // Creăm SubscriptionDto cu sursa
                SubscriptionDto subscriptionDto = new SubscriptionDto(adminMessage.getSource(), subscription);
                
                System.out.println("Adding subscription from subscriber: " + adminMessage.getSource());
                SubscriptionManager.addSubscription(subscriptionDto);
            }
            
        }

        collector.emit(new Values(adminMessage));
        collector.ack(tuple);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new org.apache.storm.tuple.Fields("subscription"));
    }
}
