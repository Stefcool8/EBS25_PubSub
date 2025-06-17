package org.pub_sub.publisher.bolt;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Tuple;
import org.pub_sub.common.generated.ForwardProto;
import org.pub_sub.common.generated.PublicationProto;

import java.util.Map;
import java.util.Properties;

public class KafkaPublisherBolt extends BaseBasicBolt {
    private transient KafkaProducer<byte[], byte[]> producer;
    private final String topic;

    public KafkaPublisherBolt(String topic) {
        this.topic = topic;
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
        PublicationProto.Publication publication = (PublicationProto.Publication) tuple.getValueByField("publication");

        String publisherId = "publisher-1";
        ForwardProto.ForwardMessage forwardMessage = ForwardProto.ForwardMessage.newBuilder()
                .setSource(publisherId)
                .setSourceType(ForwardProto.SourceType.PUBLISHER)
                .setPublication(publication)
                .build();

        byte[] data = forwardMessage.toByteArray();

        System.out.println("Publishing message to topic " + topic);// + ": " + publication.toString());

        ProducerRecord<byte[], byte[]> record = new ProducerRecord<>(topic, null, data);
        producer.send(record);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

    }
}
