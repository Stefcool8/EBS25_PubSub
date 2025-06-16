package org.pub_sub.broker.bolt;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.pub_sub.broker.dtos.SubscriptionDto;
import org.pub_sub.broker.notify.SubscriberNotifier;
import org.pub_sub.common.generated.PublicationProto;
import org.pub_sub.common.generated.SubscriptionProto;

import java.io.IOException;
import java.util.List;
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
        
        // Get matching subscriptions
        List<SubscriptionDto> matchingSubscriptions = SubscriptionManager.getMatchingSubscriptions(pub);
        
        // Notify subscribers for each matching subscription
        for (SubscriptionDto subscription : matchingSubscriptions) {
            try {
                System.out.println("Notifying subscriber for matching subscription: " + subscription.toString());
                System.out.println("subscription.hasAvgTemp() = " + subscription.hasAvgTemp());
                System.out.println("subscription.getAvgTemp() = " + subscription.getAvgTemp());
                
                if (subscription.hasAvgTemp()) {
                    // Pentru subscription-uri cu avg_temp, trimitem întregul window
                    System.out.println("Sending window data because subscription has avg_temp");
                    String windowData = SubscriptionManager.getWindowAsString();
                    System.out.println("Window data: " + windowData);
                    SubscriberNotifier.notify(subscription.getSource(), windowData);
                } else {
                    // Pentru subscription-uri normale, trimitem doar publicația curentă
                    System.out.println("Sending publication data because subscription does NOT have avg_temp");
                    SubscriberNotifier.notify(subscription.getSource(), pub.toString());
                }
            } catch (IOException e) {
                System.err.println("Error notifying subscribers: " + e.getMessage());
                e.printStackTrace();
            }
        }

        collector.emit(new Values(pub));
        collector.ack(tuple);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new org.apache.storm.tuple.Fields("publication"));
    }
}
