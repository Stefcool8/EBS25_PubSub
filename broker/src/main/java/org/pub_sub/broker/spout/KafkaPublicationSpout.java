package org.pub_sub.broker.spout;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.pub_sub.common.deserializers.ForwardDeserializer;
import org.pub_sub.common.generated.ForwardProto;

import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;

public class KafkaPublicationSpout extends BaseRichSpout {
    private KafkaConsumer<String, ForwardProto.ForwardMessage> consumer;
    private SpoutOutputCollector collector;

    @Override
    public void open(Map<String, Object> conf, TopologyContext context, SpoutOutputCollector collector) {
        this.collector = collector;
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "publication-consumer-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ForwardDeserializer.class.getName());

        consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList("pub-to-broker-1"));
    }

    @Override
    public void nextTuple() {
        ConsumerRecords<String, ForwardProto.ForwardMessage> records = consumer.poll(Duration.ofMillis(100));
        for (ConsumerRecord<String, ForwardProto.ForwardMessage> record : records) {
            ForwardProto.ForwardMessage forwardMessage = record.value();
            collector.emit(new Values(record.key(), forwardMessage.getPublication()));
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("key", "publication"));
    }
}
