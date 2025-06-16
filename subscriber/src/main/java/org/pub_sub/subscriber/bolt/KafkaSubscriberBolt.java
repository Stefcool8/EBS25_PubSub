package org.pub_sub.subscriber.bolt;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Tuple;
import org.pub_sub.common.generated.AdminProto;
import org.pub_sub.common.generated.SubscriptionProto;

import java.util.Map;
import java.util.Properties;

public class KafkaSubscriberBolt extends BaseBasicBolt {
    private transient KafkaProducer<byte[], byte[]> producer;
    private final String topic;
    private final String subscriberId;

    public KafkaSubscriberBolt(String topic, String subscriberId) {
        this.topic = topic;
        this.subscriberId = subscriberId;
    }

    @Override
    public void prepare(Map<String, Object> topoConf, TopologyContext context) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class.getName());
        producer = new KafkaProducer<>(props);
    }

    @Override
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
        AdminProto.AdminMessage adminMessage = (AdminProto.AdminMessage) tuple.getValueByField("subscription");
        byte[] data = adminMessage.toByteArray();

        ProducerRecord<byte[], byte[]> record = new ProducerRecord<>(topic, null, data);
        producer.send(record);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

    }
}
