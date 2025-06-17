package org.pub_sub.common.records;

import org.pub_sub.common.generated.SubscriptionProto;

public class SubRecord {
    public FieldCondition date;
    public FieldCondition temp;
    public FieldCondition direction;
    public FieldCondition wind;
    public FieldCondition rain;
    public FieldCondition station;
    public FieldCondition city;

    public static class FieldCondition {
        public String operator;
        public String value;
        public boolean isAverage;
    }

    public SubscriptionProto.Subscription toSubscriptionProto() {
        SubscriptionProto.Subscription.Builder subscriptionBuilder = SubscriptionProto.Subscription.newBuilder();

        if (date != null) {
            subscriptionBuilder.setDate(toStringCondition(date));
        }
        if (temp != null) {
            if (temp.isAverage) {
                subscriptionBuilder.setAvgTemp(toDoubleCondition(temp));
            } else {
                subscriptionBuilder.setTemp(toIntCondition(temp));
            }
        }
        if (direction != null) {
            subscriptionBuilder.setDirection(toStringCondition(direction));
        }
        if (wind != null) {
            subscriptionBuilder.setWind(toIntCondition(wind));
        }
        if (rain != null) {
            subscriptionBuilder.setRain(toDoubleCondition(rain));
        }
        if (station != null) {
            subscriptionBuilder.setStation(toIntCondition(station));
        }
        if (city != null) {
            subscriptionBuilder.setCity(toStringCondition(city));
        }

        return subscriptionBuilder.build();
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

    private SubscriptionProto.DoubleFieldCondition toDoubleCondition(SubRecord.FieldCondition cond) {
        return SubscriptionProto.DoubleFieldCondition.newBuilder()
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

    @Override
    public String toString() {
        // Example output:
        // { "station": { "operator": ">=", "value": "91", "isAverage": "false" }, "city": { "operator": "!=", "value": "Cluj", "isAverage": "false" }, "date": { "operator": "!=", "value": "06.08.2025", "isAverage": "false" }, "direction": { "operator": "==", "value": "NW", "isAverage": "false" }, "rain": { "operator": "==", "value": "5.58", "isAverage": "false" }, "wind": { "operator": "==", "value": "93", "isAverage": "false" }, "temp": { "operator": ">=", "value": "42", "isAverage": "false" } }
        StringBuilder sb = new StringBuilder();
        sb.append("{ ");
        if (station != null) {
            sb.append(" \"station\": {")
              .append(" \"operator\": \"").append(station.operator).append("\", ")
              .append(" \"value\": \"").append(station.value).append("\", ")
              .append(" \"isAverage\": ").append(station.isAverage)
              .append(" },\n");
        }
        if (city != null) {
            sb.append(" \"city\": {")
              .append(" \"operator\": \"").append(city.operator).append("\", ")
              .append(" \"value\": \"").append(city.value).append("\", ")
              .append(" \"isAverage\": ").append(city.isAverage)
              .append(" }, ");
        }
        if (date != null) {
            sb.append(" \"date\": {")
              .append(" \"operator\": \"").append(date.operator).append("\", ")
              .append(" \"value\": \"").append(date.value).append("\", ")
              .append(" \"isAverage\": ").append(date.isAverage)
              .append(" }, ");
        }
        if (direction != null) {
            sb.append(" \"direction\": {")
              .append(" \"operator\": \"").append(direction.operator).append("\", ")
              .append(" \"value\": \"").append(direction.value).append("\", ")
              .append(" \"isAverage\": ").append(direction.isAverage)
              .append(" }, ");
        }
        if (rain != null) {
            sb.append(" \"rain\": {")
              .append(" \"operator\": \"").append(rain.operator).append("\", ")
              .append(" \"value\": \"").append(rain.value).append("\", ")
              .append(" \"isAverage\": ").append(rain.isAverage)
              .append(" }, ");
        }
        if (wind != null) {
            sb.append(" \"wind\": {")
              .append(" \"operator\": \"").append(wind.operator).append("\", ")
              .append(" \"value\": \"").append(wind.value).append("\", ")
              .append(" \"isAverage\": ").append(wind.isAverage)
              .append(" }, ");
        }
        if (temp != null) {
            sb.append(" \"temp\": {")
              .append(" \"operator\": \"").append(temp.operator).append("\", ")
              .append(" \"value\": \"").append(temp.value).append("\", ")
              .append(" \"isAverage\": ").append(temp.isAverage)
              .append(" } ");
        }
        // Remove the last comma if it exists
        if (sb.lastIndexOf(", ") == sb.length() - 2) {
            sb.delete(sb.length() - 2, sb.length());
        }
        sb.append("}");
        return sb.toString();
    }
}
