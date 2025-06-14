package org.pub_sub.subscriber.spout;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.pub_sub.common.generated.SubscriptionProto;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

public class SubscriptionsGeneratorSpout extends BaseRichSpout {
    private SpoutOutputCollector collector;
    private BufferedReader reader;
    private ObjectMapper mapper;

    private String nextLine;

    @Override
    public void open(Map<String, Object> conf, TopologyContext context, SpoutOutputCollector spoutOutputCollector) {
        this.collector = spoutOutputCollector;
        this.mapper = new ObjectMapper();
        String filePath = "D:\\Master\\Anul1Sem2\\EBS\\EBS25_PubSub\\subscriber\\src\\main\\java\\org\\pub_sub\\subscriber\\spout\\subscriptions.txt";
        try {
            this.reader = new BufferedReader(new FileReader(filePath));
            this.nextLine = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException("Could not open file: " + filePath, e);
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

            SubscriptionProto.Subscription.Builder subscriptionBuilder = SubscriptionProto.Subscription.newBuilder();

            if (subRecord.date != null)
                subscriptionBuilder.setDate(toStringCondition(subRecord.date));
            if (subRecord.temp != null)
                subscriptionBuilder.setTemp(toIntCondition(subRecord.temp));
            if (subRecord.direction != null)
                subscriptionBuilder.setDirection(toStringCondition(subRecord.direction));
            if (subRecord.wind != null)
                subscriptionBuilder.setWind(toIntCondition(subRecord.wind));
            if (subRecord.rain != null)
                subscriptionBuilder.setRain(toFloatCondition(subRecord.rain));
            if (subRecord.station != null)
                subscriptionBuilder.setStation(toIntCondition(subRecord.station));
            if (subRecord.city != null)
                subscriptionBuilder.setCity(toStringCondition(subRecord.city));

            collector.emit(new Values(subscriptionBuilder.build()));

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


    private SubscriptionProto.StringFieldCondition toStringCondition(SubRecord.FieldCondition cond) {
        return SubscriptionProto.StringFieldCondition.newBuilder()
                .setOperator(toOperator(cond.operator))
                .setValue(cond.value)
                .build();
    }

    private SubscriptionProto.IntFieldCondition toIntCondition(SubRecord.FieldCondition cond) {
        return SubscriptionProto.IntFieldCondition.newBuilder()
                .setOperator(toOperator(cond.operator))
                .setValue(Integer.parseInt(cond.value))
                .build();
    }

    private SubscriptionProto.FloatFieldCondition toFloatCondition(SubRecord.FieldCondition cond) {
        return SubscriptionProto.FloatFieldCondition.newBuilder()
                .setOperator(toOperator(cond.operator))
                .setValue(Float.parseFloat(cond.value))
                .build();
    }

    private SubscriptionProto.Operator toOperator(String op) {
        return switch (op) {
            case "==" -> SubscriptionProto.Operator.EQ;
            case "!=" -> SubscriptionProto.Operator.NE;
            case "<"  -> SubscriptionProto.Operator.LT;
            case "<=" -> SubscriptionProto.Operator.LE;
            case ">"  -> SubscriptionProto.Operator.GT;
            case ">=" -> SubscriptionProto.Operator.GE;
            default   -> throw new IllegalArgumentException("Invalid operator: " + op);
        };
    }
}