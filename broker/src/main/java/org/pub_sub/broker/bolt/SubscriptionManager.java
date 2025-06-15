package org.pub_sub.broker.bolt;

import org.pub_sub.common.generated.PublicationProto;
import org.pub_sub.common.generated.SubscriptionProto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class SubscriptionManager {
    private static final List<SubscriptionProto.Subscription> subscriptions = new ArrayList<>();
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    // de adaugat syncronized list aici!
    public static void addSubscription(SubscriptionProto.Subscription subscription) {
        subscriptions.add(subscription);
    }

    public static List<SubscriptionProto.Subscription> getMatchingSubscriptions(PublicationProto.Publication publication) {
        List<SubscriptionProto.Subscription> matchingSubscriptions = new ArrayList<>();
        
        for (SubscriptionProto.Subscription subscription : subscriptions) {
            if (matchesSubscription(publication, subscription)) {
                matchingSubscriptions.add(subscription);
            }
        }
        
        return matchingSubscriptions;
    }

    private static boolean matchesSubscription(PublicationProto.Publication publication, SubscriptionProto.Subscription subscription) {
        // Check date field
        if (subscription.hasDate()) {
            if (!matchesDateField(publication.getDate(), subscription.getDate())) {
                return false;
            }
        }

        // Check temp field
        if (subscription.hasTemp()) {
            if (!matchesIntField(publication.getTemp(), subscription.getTemp())) {
                return false;
            }
        }

        // Check direction field
        if (subscription.hasDirection()) {
            if (!matchesStringField(publication.getDirection(), subscription.getDirection())) {
                return false;
            }
        }

        // Check wind field
        if (subscription.hasWind()) {
            if (!matchesIntField(publication.getWind(), subscription.getWind())) {
                return false;
            }
        }

        // Check rain field
        if (subscription.hasRain()) {
            if (!matchesFloatField(publication.getRain(), subscription.getRain())) {
                return false;
            }
        }

        // Check station field
        if (subscription.hasStation()) {
            if (!matchesIntField(publication.getStation(), subscription.getStation())) {
                return false;
            }
        }

        // Check city field
        if (subscription.hasCity()) {
            if (!matchesStringField(publication.getCity(), subscription.getCity())) {
                return false;
            }
        }

        // // Check avg_temp field
        // if (subscription.hasAvgTemp()) {
        //     if (!matchesFloatField(publication.getAvgTemp(), subscription.getAvgTemp())) {
        //         return false;
        //     }
        // }

        return true;
    }

    private static boolean matchesDateField(String pubDate, SubscriptionProto.StringFieldCondition subDate) {
        try {
            LocalDate pubLocalDate = LocalDate.parse(pubDate, DATE_FORMATTER);
            LocalDate subLocalDate = LocalDate.parse(subDate.getValue(), DATE_FORMATTER);
            int comparison = pubLocalDate.compareTo(subLocalDate);

            switch (subDate.getOperator()) {
                case EQ: return comparison == 0;
                case NE: return comparison != 0;
                case GT: return comparison > 0;
                case GE: return comparison >= 0;
                case LT: return comparison < 0;
                case LE: return comparison <= 0;
                default: return false;
            }
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    private static boolean matchesStringField(String pubValue, SubscriptionProto.StringFieldCondition subValue) {
        switch (subValue.getOperator()) {
            case EQ: return pubValue.equals(subValue.getValue());
            case NE: return !pubValue.equals(subValue.getValue());
            case GT: return pubValue.compareTo(subValue.getValue()) > 0;
            case GE: return pubValue.compareTo(subValue.getValue()) >= 0;
            case LT: return pubValue.compareTo(subValue.getValue()) < 0;
            case LE: return pubValue.compareTo(subValue.getValue()) <= 0;
            default: return false;
        }
    }

    private static boolean matchesIntField(int pubValue, SubscriptionProto.IntFieldCondition subValue) {
        switch (subValue.getOperator()) {
            case EQ: return pubValue == subValue.getValue();
            case NE: return pubValue != subValue.getValue();
            case GT: return pubValue > subValue.getValue();
            case GE: return pubValue >= subValue.getValue();
            case LT: return pubValue < subValue.getValue();
            case LE: return pubValue <= subValue.getValue();
            default: return false;
        }
    }

    private static boolean matchesFloatField(float pubValue, SubscriptionProto.FloatFieldCondition subValue) {
        switch (subValue.getOperator()) {
            case EQ: return pubValue == subValue.getValue();
            case NE: return pubValue != subValue.getValue();
            case GT: return pubValue > subValue.getValue();
            case GE: return pubValue >= subValue.getValue();
            case LT: return pubValue < subValue.getValue();
            case LE: return pubValue <= subValue.getValue();
            default: return false;
        }
    }
} 