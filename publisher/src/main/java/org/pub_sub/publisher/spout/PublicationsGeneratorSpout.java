package org.pub_sub.publisher.spout;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;
import org.pub_sub.common.generated.PublicationProto;
import org.pub_sub.generator.Publication;
import org.pub_sub.generator.generators.ParallelPublicationsGenerator;
import org.pub_sub.generator.generators.PublicationsGenerator;
import org.pub_sub.generator.schema.SchemaBuilder;
import org.pub_sub.generator.schema.SchemaFields;
import org.pub_sub.generator.storage.PublicationSaver;
import org.pub_sub.common.records.PubRecord;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class PublicationsGeneratorSpout extends BaseRichSpout {
    private SpoutOutputCollector collector;
    private BlockingQueue<Publication> queue;
    private volatile boolean done = false;
    private long startMs;
    private long durationMs;

    @Override
    public void open(Map<String, Object> conf, TopologyContext ctx, SpoutOutputCollector coll) {
        this.collector = coll;
        int totalPubs  = (Integer) conf.getOrDefault("pub.total", 10000);
        int threads    = (Integer) conf.getOrDefault("pub.threads", 4);
        queue = new LinkedBlockingQueue<>(totalPubs);
        this.startMs    = System.currentTimeMillis();
        this.durationMs = TimeUnit.MINUTES.toMillis(3);

        new Thread(() -> {
//            try {
//                ParallelPublicationsGenerator.generatePublicationsMultithreaded(
//                        SchemaBuilder.build(),
//                        threads,
//                        totalPubs,
//                        new PublicationSaver() {
//                            @Override public void save(Publication p) throws InterruptedException {
//                                queue.put(p);
//                            }
//                            @Override public void close() {}
//                        }
//                );
            PublicationsGenerator pubGen = new PublicationsGenerator(SchemaBuilder.build(), totalPubs);
            pubGen.setPublicationSaver(new PublicationSaver() {
                @Override
                public void save(Publication p) throws InterruptedException {
                    long elapsed = System.currentTimeMillis() - startMs;
                    if (elapsed < durationMs) {
                        queue.put(p);
                    } else {
                        throw new RuntimeException("Generation timeout");
                    }
                }

                @Override
                public void close() {
                }
            });
            try {
                pubGen.generatePublications();
            } catch (RuntimeException e) {
                // we expect our timeout exception here
            } finally {
                done = true;
            }}, "pub-gen-thread").start();
    }

    @Override
    public void nextTuple() {
        // Hard stop emission after window
        if (System.currentTimeMillis() - startMs > durationMs) {
            Utils.sleep(1000);
            return;
        }

        try {
            Publication p = queue.poll(100, TimeUnit.MILLISECONDS);
            if (p != null) {
                PublicationProto.Publication protoPub = toProto(p);

                // Map the protobuf publication to PubRecord
                PubRecord pubRecord = new PubRecord(
                        protoPub.getStation(),
                        protoPub.getCity(),
                        protoPub.getDate(),
                        protoPub.getDirection(),
                        protoPub.getRain(),
                        protoPub.getWind(),
                        protoPub.getTemp(),
                        protoPub.getTimestamp()
                );

                // Save the publication to file
                savePublication(pubRecord);

                // Emit the publication as a protobuf message
                collector.emit(new Values(protoPub));

                Utils.sleep(100); // Sleep to decrease the emission rate
            } else if (done && queue.isEmpty()) {
                // queue drained after generation finished
                Utils.sleep(1000);
            } else {
                // If no publication is available, wait a bit before checking again
                Utils.sleep(50);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private PublicationProto.Publication toProto(Publication p) {
        PublicationProto.Publication.Builder b = PublicationProto.Publication.newBuilder();

        // Extract raw string values
        String stationStr   = p.fields.get(SchemaFields.STATION);
        String cityStr      = p.fields.get(SchemaFields.CITY);
        String tempStr      = p.fields.get(SchemaFields.TEMP);
        String rainStr      = p.fields.get(SchemaFields.RAIN);
        String windStr      = p.fields.get(SchemaFields.WIND);
        String directionStr = p.fields.get(SchemaFields.DIRECTION);
        String dateStr      = p.fields.get(SchemaFields.DATE);

        // Parse and set fields
        if (stationStr != null)   b.setStation(Integer.parseInt(stationStr));
        if (cityStr != null)      b.setCity(cityStr);
        if (tempStr != null)      b.setTemp(Integer.parseInt(tempStr));
        if (rainStr != null)      b.setRain((float) Double.parseDouble(rainStr));
        if (windStr != null)      b.setWind(Integer.parseInt(windStr));
        if (directionStr != null) b.setDirection(directionStr);
        if (dateStr != null)      b.setDate(dateStr);

        b.setTimestamp(new Date().getTime());

        return b.build();
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("publication"));
    }

    private void savePublication(PubRecord pubRecord) {
        // Save the publication to a file
        try {
            java.nio.file.Files.writeString(
                    java.nio.file.Paths.get("publications.txt"),
                    pubRecord + "\n",
                    java.nio.charset.StandardCharsets.UTF_8,
                    java.nio.file.StandardOpenOption.CREATE,
                    java.nio.file.StandardOpenOption.APPEND
            );
        } catch (java.io.IOException e) {
            System.err.println("Error writing to publications.txt: " + e.getMessage());
        }
    }
}
