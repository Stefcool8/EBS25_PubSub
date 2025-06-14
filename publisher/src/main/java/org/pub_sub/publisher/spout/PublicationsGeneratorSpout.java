package org.pub_sub.publisher.spout;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.pub_sub.common.generated.PublicationProto;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

public class PublicationsGeneratorSpout extends BaseRichSpout {
    private SpoutOutputCollector collector;
    private BufferedReader reader;
    private ObjectMapper mapper;

    private String nextLine;

    @Override
    public void open(Map<String, Object> map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        this.collector = spoutOutputCollector;
        this.mapper = new ObjectMapper();
        String filePath = "D:\\Master\\Anul1Sem2\\EBS\\EBS25_PubSub\\publisher\\src\\main\\java\\org\\pub_sub\\publisher\\spout\\publications.txt";
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
            PubRecord record = mapper.readValue(nextLine, PubRecord.class);
            PublicationProto.Publication publication = PublicationProto.Publication.newBuilder()
                    .setDate(record.date)
                    .setTemp(record.temp)
                    .setDirection(record.direction)
                    .setWind(record.wind)
                    .setRain(record.rain)
                    .setStation(record.station)
                    .setCity(record.city)
                    .build();

            collector.emit(new Values(publication));

            nextLine = reader.readLine();

            Thread.sleep(200);

        } catch (Exception e) {
            throw new RuntimeException("Error reading or parsing line from file", e);
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("publication"));
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
