package org.pub_sub.subscriber.spout;

public class SubRecord {
    public FieldCondition date;
    public FieldCondition temp;
    public FieldCondition direction;
    public FieldCondition wind;
    public FieldCondition rain;
    public FieldCondition station;
    public FieldCondition city;
    public FieldCondition avg_temp;

    public static class FieldCondition {
        public String operator;
        public String value;
    }
}
