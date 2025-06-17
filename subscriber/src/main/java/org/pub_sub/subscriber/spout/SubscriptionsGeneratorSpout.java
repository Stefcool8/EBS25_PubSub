package org.pub_sub.subscriber.spout;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.pub_sub.common.generated.AdminProto;
import org.pub_sub.common.generated.SubscriptionProto;
import org.pub_sub.common.records.SubRecord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

public class SubscriptionsGeneratorSpout extends BaseRichSpout {
    private SpoutOutputCollector collector;
    private BufferedReader reader;
    private ObjectMapper mapper;
    private String nextLine;
    private final String source;
    private final String port;

    public SubscriptionsGeneratorSpout(String source, String port) {
        this.source = source;
        this.port = port;
    }

    @Override
    public void open(Map<String, Object> conf, TopologyContext context, SpoutOutputCollector spoutOutputCollector) {
        this.collector = spoutOutputCollector;
        this.mapper = new ObjectMapper();
        try {
            var inputStream = getClass().getResourceAsStream("subscriptions-" + port + ".txt");
            if (inputStream == null) {
                throw new RuntimeException("Could not find subscriptions-" + port + ".txt in the same directory as " + getClass().getName());
            }
            this.reader = new BufferedReader(new InputStreamReader(inputStream));
            this.nextLine = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException("Could not open subscriptions file", e);
        }
    }

    @Override
    public void nextTuple() {
        if (nextLine == null) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {}
            return;
        }

        try {
            SubRecord subRecord = mapper.readValue(nextLine, SubRecord.class);
            SubscriptionProto.Subscription subProto = subRecord.toSubscriptionProto();

            AdminProto.AdminMessage adminMessage = AdminProto.AdminMessage.newBuilder()
                    .setSource(source)
                    .setSourceType(AdminProto.SourceType.SUBSCRIBER)
                    .addSubscriptions(subProto)
                    .build();

            collector.emit(new Values(adminMessage));

            nextLine = reader.readLine();
            Thread.sleep(200);

        } catch (Exception e) {
            throw new RuntimeException("Error reading or parsing subscription line", e);
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("subscription"));
    }

    @Override
    public void close() {
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}