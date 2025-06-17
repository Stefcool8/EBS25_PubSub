package org.pub_sub;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.pub_sub.broker.bolt.SubscriptionManager;
import org.pub_sub.broker.dtos.SubscriptionDto;
import org.pub_sub.common.generated.AdminProto;
import org.pub_sub.common.generated.PublicationProto;
import org.pub_sub.common.generated.SubscriptionProto;
import org.pub_sub.common.records.SubRecord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class EvaluationC {
    static final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) {
        List<PublicationProto.Publication> pubs = EvaluationA.loadPublications();

        List<SubscriptionDto> subsEq100 = loadSubscriptions("subscriptions_eq100.txt");
        List<SubscriptionDto> subsEq25  = loadSubscriptions("subscriptions_eq25.txt");

        double rate100 = computeMatchingRate(pubs, subsEq100);
        double rate25  = computeMatchingRate(pubs, subsEq25);

        System.out.printf("Matching rate with 100%% equals on field: %.2f%%%n", rate100*100);
        System.out.printf("Matching rate with  25%% equals on field: %.2f%%%n", rate25*100);
    }

    /** Compute: (total matched pairs) / (pubCount * subCount) */
    private static double computeMatchingRate(
            List<PublicationProto.Publication> pubs,
            List<SubscriptionDto> subs
    ) {
        long totalMatches = 0;
        SubscriptionManager.subscriptions = new ArrayList<>(subs);

        for (PublicationProto.Publication pub : pubs) {
            for (SubscriptionDto sub : subs) {
                if (SubscriptionManager.matchesSubscription(pub, sub)) {
                    totalMatches++;
                }
            }
        }
        long denom = (long) pubs.size() * subs.size();
        return denom == 0 ? 0.0 : (double) totalMatches / denom;
    }

    public static List<SubscriptionDto> loadSubscriptions(String file) {
        try {
            InputStream inputStream = EvaluationC.class.getResourceAsStream(file);
            if (inputStream == null) {
                throw new RuntimeException("Could not find " + file + " in the same directory as " + EvaluationC.class.getName());
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            List<SubscriptionDto> subscriptions = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                SubRecord subRecord = mapper.readValue(line, SubRecord.class);
                SubscriptionProto.Subscription subProto = subRecord.toSubscriptionProto();
                subscriptions.add(
                        new SubscriptionDto("", AdminProto.SourceType.SUBSCRIBER, subProto)
                );
            }
            reader.close();
            return subscriptions;
        } catch (IOException e) {
            throw new RuntimeException("Error reading subscriptions from file: " + file, e);
        }
    }
}
