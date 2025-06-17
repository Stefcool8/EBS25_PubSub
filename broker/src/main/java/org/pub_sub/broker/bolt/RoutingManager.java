package org.pub_sub.broker.bolt;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.pub_sub.broker.dtos.Pair;
import org.pub_sub.broker.dtos.SubscriptionDto;
import org.pub_sub.broker.notify.SubscriberNotifier;
import org.pub_sub.common.generated.AdminProto;
import org.pub_sub.common.generated.ForwardProto;
import org.pub_sub.common.generated.PublicationProto;
import org.pub_sub.common.generated.SubscriptionProto;
import org.pub_sub.common.records.PubRecord;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class RoutingManager {
    public static void handleNotification(KafkaProducer<byte[], byte[]> producer, String brokerId, ForwardProto.ForwardMessage forwardMessage, String[] neighboringBrokers)
    {
        PublicationProto.Publication protoPub = forwardMessage.getPublication();

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

        // Get matching subscriptions
        List<SubscriptionDto> matchingSubscriptions = SubscriptionManager.getMatchingSubscriptions(protoPub);

        // Send a notification to a subscriber once. If there are multiple subscriptions for the same subscriber, we only send one notification.
        Set<String> notifiedNodes = new HashSet<>();

        // Notify subscribers for each matching subscription
        for (SubscriptionDto subscription : matchingSubscriptions) {
            try {
                if (
                        subscription.getSource().equals(forwardMessage.getSource()) &&
                        forwardMessage.getSourceType().equals(ForwardProto.SourceType.BROKER) &&
                        subscription.getSourceType().equals(AdminProto.SourceType.BROKER)
                    )
                {
                    // Skip notifications for the source of the publication
                    continue;
                }

                if (notifiedNodes.contains(subscription.getSource())) {
                    // Skip if we already notified this subscriber
                    continue;
                }

                if (subscription.getSourceType().equals(AdminProto.SourceType.SUBSCRIBER)) {
                    System.out.println("Notifying subscriber for matching subscription: " + subscription);
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
                        String json = new ObjectMapper().writeValueAsString(pubRecord);
                        SubscriberNotifier.notify(subscription.getSource(), json);

                        // Add to notified subscribers to avoid duplicate notifications
                        notifiedNodes.add(subscription.getSource());
                    }
                }
                else
                {
                    // send to neighboring brokers
                    ForwardProto.ForwardMessage message = ForwardProto.ForwardMessage.newBuilder()
                            .setSource(brokerId)
                            .setSourceType(ForwardProto.SourceType.BROKER)
                            .setPublication(protoPub)
                            .build();

                    byte[] data = message.toByteArray();

                    if (Arrays.stream(neighboringBrokers).anyMatch(n -> n.equals(subscription.getSource()))) {
                        System.out.println("Sending forward message to broker: " + subscription.getSource());
                    } else {
                        System.out.println("Skipping forward message for non-neighboring broker: " + subscription.getSource());
                        continue;
                    }

                    ProducerRecord<byte[], byte[]> record = new ProducerRecord<>("broker-"+subscription.getSource()+"-forward", null, data);
                    producer.send(record);

                    notifiedNodes.add(subscription.getSource());
                }
            } catch (IOException e) {
                System.err.println("Error forwarding notification: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public static Pair<Set<SubscriptionDto>, Set<SubscriptionDto>> administer(AdminProto.AdminMessage adminMessage, String[] neighboringBrokers)
    {
        for (SubscriptionProto.Subscription subscription : adminMessage.getSubscriptionsList()) {
            SubscriptionDto subscriptionDto = new SubscriptionDto(adminMessage.getSource(), adminMessage.getSourceType(), subscription);
            SubscriptionManager.addSubscription(subscriptionDto);
        }

        for (SubscriptionProto.Subscription subscription : adminMessage.getUnsubscriptionsList()) {
            SubscriptionDto subscriptionDto = new SubscriptionDto(adminMessage.getSource(), adminMessage.getSourceType(), subscription);
            SubscriptionManager.removeSubscription(subscriptionDto);
        }

        Set<SubscriptionDto> subscriptions = new HashSet<>();
        Set<SubscriptionDto> unsubscriptions = new HashSet<>();

        for (String neighbor : neighboringBrokers) {
            if (neighbor.equals(adminMessage.getSource()) && adminMessage.getSourceType() == AdminProto.SourceType.BROKER) {
                continue;
            }

            for (SubscriptionProto.Subscription subscription : adminMessage.getSubscriptionsList()) {
                SubscriptionDto subscriptionDto = new SubscriptionDto(neighbor, AdminProto.SourceType.BROKER, subscription);
                subscriptions.add(subscriptionDto);
            }

            for (SubscriptionProto.Subscription subscription : adminMessage.getUnsubscriptionsList()) {
                SubscriptionDto subscriptionDto = new SubscriptionDto(neighbor, AdminProto.SourceType.BROKER, subscription);
                unsubscriptions.add(subscriptionDto);
            }
        }

        return new Pair<>(subscriptions, unsubscriptions);
    }

    public static void handleAdminMessage(KafkaProducer<byte[], byte[]> producer, String currentBrokerId, String source, Set<SubscriptionDto> subscriptions, Set<SubscriptionDto> unsubscriptions, String[] neighboringBrokers) {
        for (String neighbor : neighboringBrokers) {
            if (neighbor.equals(source)) {
                continue;
            }

            Set<SubscriptionDto> neighborSubscriptions = subscriptions.stream().filter(
                subscription -> subscription.getSource().equals(neighbor) && subscription.getSourceType().equals(AdminProto.SourceType.BROKER)
            ).collect(Collectors.toSet());
            Set<SubscriptionDto> neighborUnsubscriptions = unsubscriptions.stream().filter(
                subscription -> subscription.getSource().equals(neighbor) && subscription.getSourceType().equals(AdminProto.SourceType.BROKER)
            ).collect(Collectors.toSet());

            if (!neighborSubscriptions.isEmpty() || !neighborUnsubscriptions.isEmpty()) {
                AdminProto.AdminMessage adminMessage = AdminProto.AdminMessage.newBuilder()
                        .setSource(currentBrokerId)
                        .setSourceType(AdminProto.SourceType.BROKER)
                        .addAllSubscriptions(neighborSubscriptions.stream().map(SubscriptionDto::toProto).collect(Collectors.toList()))
                        .addAllUnsubscriptions(neighborUnsubscriptions.stream().map(SubscriptionDto::toProto).collect(Collectors.toList()))
                        .build();

                // Send the admin message to the neighboring broker
                byte[] data = adminMessage.toByteArray();
                ProducerRecord<byte[], byte[]> record = new ProducerRecord<>("broker-" + neighbor + "-admin", null, data);
                producer.send(record);
            }
        }
    }
}
