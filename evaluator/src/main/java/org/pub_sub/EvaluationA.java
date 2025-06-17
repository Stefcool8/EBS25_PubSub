package org.pub_sub;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.pub_sub.broker.bolt.SubscriptionManager;
import org.pub_sub.broker.dtos.SubscriptionDto;
import org.pub_sub.common.generated.AdminProto;
import org.pub_sub.common.generated.PublicationProto;
import org.pub_sub.common.generated.SubscriptionProto;
import org.pub_sub.common.records.PubRecord;
import org.pub_sub.common.records.SubRecord;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EvaluationA {
    static final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) {
        // Load publications
        List<PublicationProto.Publication> publications = loadPublications();

        String[] ports = {"8082", "8083", "8084"};

        for (String port : ports) {
            List<SubscriptionDto> subs = loadSubscriptions("subscriber/src/main/java/org/pub_sub/subscriber/spout/subscriptions-" + port + ".txt");
            Set<String> deliveredPublicationTimestamps = loadNotifications("notifications-" + port + ".txt");

            // Check which publications were delivered to at least one matching subscription
            int deliveredCount = 0;
            int expectedCount = 0;

            for (PublicationProto.Publication pub : publications) {
                SubscriptionManager.subscriptions = new ArrayList<>(subs);
                boolean shouldBeDelivered = false;

                for (SubscriptionDto subscription : SubscriptionManager.subscriptions) {
                    if (SubscriptionManager.matchesSubscription(pub, subscription)) {
                        shouldBeDelivered = true;
                        break; // No need to check further subscriptions for this publication
                    }
                }
                if (shouldBeDelivered) {
                    expectedCount++;
                    if (deliveredPublicationTimestamps.contains(String.valueOf(pub.getTimestamp()))) {
                        deliveredCount++;
                    }
                }
            }

            System.out.println("Port: " + port);
            System.out.println("Expected publications delivered: " + expectedCount);
            System.out.println("Actual publications delivered: " + deliveredCount);
            if (expectedCount > 0) {
                double percentage = (double) deliveredCount / expectedCount * 100;
                System.out.printf("Percentage of publications delivered: %.2f%%\n", percentage);
            } else {
                System.out.println("No expected publications to deliver.");
            }
            System.out.println("-----------------------------");
        }
    }

    public static List<SubscriptionDto> loadSubscriptions(String file) {
        Path subFile = Path.of(file);
        try (BufferedReader reader = Files.newBufferedReader(subFile, StandardCharsets.UTF_8)) {
            List<SubscriptionDto> subscriptions = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                SubRecord subRecord = mapper.readValue(line, SubRecord.class);
                SubscriptionProto.Subscription subProto = subRecord.toSubscriptionProto();
                subscriptions.add(
                        new SubscriptionDto("", AdminProto.SourceType.SUBSCRIBER, subProto)
                );
            }
            return subscriptions;
        } catch (IOException e) {
            throw new UncheckedIOException("Cannot read " + subFile, e);
        }
    }

    public static Set<String> loadNotifications(String file) {
        Set<String> timestamps = new HashSet<>();
        try {
            InputStream inputStream = EvaluationA.class.getResourceAsStream(file);
            if (inputStream == null) {
                throw new RuntimeException("Could not find " + file + " in the same directory as " + EvaluationA.class.getName());
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts.length > 0) {
                    timestamps.add(parts[0].trim());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading notifications from file: " + file, e);
        }
        return timestamps;
    }

    public static List<PublicationProto.Publication> loadPublications() {
        try {
            InputStream inputStream = EvaluationA.class.getResourceAsStream("publications.txt");
            if (inputStream == null) {
                throw new RuntimeException("Could not find " + "publications.txt" + " in the same directory as " + EvaluationA.class.getName());
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            List<PublicationProto.Publication> publications = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                PubRecord pubRecord = mapper.readValue(line, PubRecord.class);
                PublicationProto.Publication pubProto = pubRecord.toPublicationProto();
                publications.add(pubProto);
            }
            reader.close();
            return publications;
        } catch (IOException e) {
            throw new RuntimeException("Error reading subscriptions from file: " + "publications.txt", e);
        }
    }
}
