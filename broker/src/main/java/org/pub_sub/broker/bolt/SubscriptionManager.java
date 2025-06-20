package org.pub_sub.broker.bolt;

import org.pub_sub.broker.dtos.SubscriptionDto;
import org.pub_sub.common.generated.PublicationProto;
import org.pub_sub.common.generated.SubscriptionProto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.LinkedList;

public class SubscriptionManager {
    public static List<SubscriptionDto> subscriptions = Collections.synchronizedList(new ArrayList<>());
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final int WINDOW_SIZE = 3; 
    private static final Queue<PublicationProto.Publication> window = new LinkedList<>();

    public static void addSubscription(SubscriptionDto subscription) {
        subscriptions.add(subscription);
        System.out.println("Added subscription: " + subscription.toString());
        System.out.println("Total subscriptions: " + subscriptions.size());
    }

    public static void removeSubscription(SubscriptionDto subscription) {
        subscriptions.remove(subscription);
        System.out.println("Removed subscription: " + subscription.toString());
        System.out.println("Total subscriptions: " + subscriptions.size());
    }

    public static List<SubscriptionDto> getMatchingSubscriptions(PublicationProto.Publication publication) {
        List<SubscriptionDto> matchingSubscriptions = new ArrayList<>();

        synchronized (window) {
            window.add(publication);
            if (window.size() > WINDOW_SIZE) {
                // goleste complet window-ul
                window.clear();
            }
            System.out.println("Window updated. Size: " + window.size() + "/" + WINDOW_SIZE);
        }

        synchronized (subscriptions) {
            System.out.println("Checking " + subscriptions.size() + " subscriptions for publication: " + publication.toString());
            for (SubscriptionDto subscription : subscriptions) {
                if (matchesSubscription(publication, subscription)) {
                    matchingSubscriptions.add(subscription);
                    System.out.println("Found matching subscription!");
                }
            }
        }
        
        System.out.println("Total matching subscriptions: " + matchingSubscriptions.size());
        return matchingSubscriptions;
    }

    public static String getWindowAsString() {
        synchronized (window) {
            if (window.size() < WINDOW_SIZE) {
                return "Window not full yet. Current size: " + window.size() + "/" + WINDOW_SIZE;
            }
            
            StringBuilder sb = new StringBuilder();
            sb.append("Window Data (").append(window.size()).append(" publications):\n");
            
            int index = 1;
            for (PublicationProto.Publication pub : window) {
                sb.append("Publication ").append(index++).append(": ").append(pub.toString()).append("\n");
            }

            float sum = 0.0f;
            for (PublicationProto.Publication pub : window) {
                sum += pub.getTemp();
            }
            float avgTemp = sum / window.size();
            sb.append("Average Temperature: ").append(avgTemp).append("\n");
            
            return sb.toString();
        }
    }

    public static boolean matchesSubscription(PublicationProto.Publication publication, SubscriptionDto subscription) {
        if (subscription.hasAvgTemp()) {
            synchronized (window) {
                if (window.size() < WINDOW_SIZE) {
                    return false;
                }
                
                return matchesSubscriptionOnWindow(subscription);
            }
        } else {
            return matchesSubscriptionOnPublication(publication, subscription);
        }
    }

    private static boolean matchesSubscriptionOnWindow(SubscriptionDto subscription) {
        for (PublicationProto.Publication pub : window) {
            if (!matchesSubscriptionOnPublication(pub, subscription)) {
                return false;
            }
        }

        if (subscription.hasAvgTemp()) {
            float sum = 0.0f;
            for (PublicationProto.Publication pub : window) {
                sum += pub.getTemp();
            }
            float avgTemp = sum / WINDOW_SIZE;
            
            if (!matchesFloatField(avgTemp, subscription.getAvgTemp(), subscription.getAvgTempOperator())) {
                return false;
            }
        }
        
        return true;
    }

    private static boolean matchesSubscriptionOnPublication(PublicationProto.Publication publication, SubscriptionDto subscription) {
        if (subscription.hasDate()) {
            if (!matchesDateField(publication.getDate(), subscription.getDate(), subscription.getDateOperator())) {
                return false;
            }
        }

        if (subscription.hasTemp()) {
            if (!matchesIntField(publication.getTemp(), subscription.getTemp(), subscription.getTempOperator())) {
                return false;
            }
        }

        if (subscription.hasDirection()) {
            if (!matchesStringField(publication.getDirection(), subscription.getDirection(), subscription.getDirectionOperator())) {
                return false;
            }
        }

        if (subscription.hasWind()) {
            if (!matchesIntField(publication.getWind(), subscription.getWind(), subscription.getWindOperator())) {
                return false;
            }
        }

        if (subscription.hasRain()) {
            if (!matchesFloatField(publication.getRain(), subscription.getRain(), subscription.getRainOperator())) {
                return false;
            }
        }

        if (subscription.hasStation()) {
            if (!matchesIntField(publication.getStation(), subscription.getStation(), subscription.getStationOperator())) {
                return false;
            }
        }

        if (subscription.hasCity()) {
            if (!matchesStringField(publication.getCity(), subscription.getCity(), subscription.getCityOperator())) {
                return false;
            }
        }

        return true;
    }

    private static boolean matchesDateField(String pubDate, String subDate, SubscriptionProto.Operator operator) {
        try {
            LocalDate pubLocalDate = LocalDate.parse(pubDate, DATE_FORMATTER);
            LocalDate subLocalDate = LocalDate.parse(subDate, DATE_FORMATTER);
            int comparison = pubLocalDate.compareTo(subLocalDate);

            switch (operator) {
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

    private static boolean matchesStringField(String pubValue, String subValue, SubscriptionProto.Operator operator) {
        switch (operator) {
            case EQ: return pubValue.equals(subValue);
            case NE: return !pubValue.equals(subValue);
            case GT: return pubValue.compareTo(subValue) > 0;
            case GE: return pubValue.compareTo(subValue) >= 0;
            case LT: return pubValue.compareTo(subValue) < 0;
            case LE: return pubValue.compareTo(subValue) <= 0;
            default: return false;
        }
    }

    private static boolean matchesIntField(int pubValue, int subValue, SubscriptionProto.Operator operator) {
        switch (operator) {
            case EQ: return pubValue == subValue;
            case NE: return pubValue != subValue;
            case GT: return pubValue > subValue;
            case GE: return pubValue >= subValue;
            case LT: return pubValue < subValue;
            case LE: return pubValue <= subValue;
            default: return false;
        }
    }

    private static boolean matchesFloatField(double pubValue, double subValue, SubscriptionProto.Operator operator) {
        switch (operator) {
            case EQ: return Double.compare(pubValue, subValue) == 0;
            case NE: return Double.compare(pubValue, subValue) != 0;
            case GT: return pubValue > subValue;
            case GE: return pubValue >= subValue;
            case LT: return pubValue < subValue;
            case LE: return pubValue <= subValue;
            default: return false;
        }
    }
} 