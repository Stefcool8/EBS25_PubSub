package org.pub_sub.broker.bolt;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.pub_sub.common.generated.ForwardProto;

import java.util.Map;
import java.util.Properties;

public class ForwardMessageBolt extends BaseRichBolt {

    private OutputCollector collector;
    private final String brokerId;
    private final String[] neighboringBrokers;
    private transient KafkaProducer<byte[], byte[]> producer;

    public ForwardMessageBolt(String brokerId, String[] neighboringBrokers) {
        this.brokerId = brokerId;
        this.neighboringBrokers = neighboringBrokers;
    }

    @Override
    public void prepare(Map<String, Object> map, TopologyContext topologyContext, OutputCollector outputCollector) {
        this.collector = outputCollector;
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class.getName());
        this.producer = new KafkaProducer<>(props);
    }

    @Override
    public void execute(Tuple tuple) {
        ForwardProto.ForwardMessage forwardMessage = (ForwardProto.ForwardMessage) tuple.getValueByField("forwardMessage");

        System.out.println("Received publication: " + forwardMessage.getPublication());
        
        RoutingManager.handleNotification(producer, brokerId, forwardMessage, neighboringBrokers);

        collector.emit(new Values(forwardMessage));
        collector.ack(tuple);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new org.apache.storm.tuple.Fields("forwardMessage"));
    }
}
