package org.pub_sub.broker.bolt;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.pub_sub.broker.dtos.Pair;
import org.pub_sub.broker.dtos.SubscriptionDto;
import org.pub_sub.common.generated.AdminProto;

import java.util.Map;
import java.util.Set;

public class AdminMessageBolt extends BaseRichBolt {
    private OutputCollector collector;
    private final String brokerId;
    private final String[] neighboringBrokers;

    public AdminMessageBolt(String brokerId, String[] neighboringBrokers) {
        this.brokerId = brokerId;
        this.neighboringBrokers = neighboringBrokers;
    }

    @Override
    public void prepare(Map<String, Object> map, TopologyContext topologyContext, OutputCollector outputCollector) {
        this.collector = outputCollector;
    }

    @Override
    public void execute(Tuple tuple) {
        AdminProto.AdminMessage adminMessage = (AdminProto.AdminMessage) tuple.getValueByField("adminMessage");

        System.out.println("Received admin message: " + adminMessage.toString());
        
        /*// Procesăm doar mesajele de la subscriberi
        if (adminMessage.getSourceType() == AdminProto.SourceType.SUBSCRIBER) {
            // Procesăm abonările
            for (SubscriptionProto.Subscription subscription : adminMessage.getSubscriptionsList()) {
                // Creăm SubscriptionDto cu sursa
                SubscriptionDto subscriptionDto = new SubscriptionDto(adminMessage.getSource(), adminMessage.getSourceType(), subscription);
                
                System.out.println("Adding subscription from subscriber: " + adminMessage.getSource());
                SubscriptionManager.addSubscription(subscriptionDto);
            }
        }*/

        Pair<Set<SubscriptionDto>, Set<SubscriptionDto>> administerResult = RoutingManager.administer(adminMessage, neighboringBrokers);
        RoutingManager.handleAdminMessage(brokerId, adminMessage.getSource(), administerResult.getFirst(), administerResult.getSecond(), neighboringBrokers);

        collector.emit(new Values(adminMessage));
        collector.ack(tuple);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new org.apache.storm.tuple.Fields("adminMessage"));
    }
}
