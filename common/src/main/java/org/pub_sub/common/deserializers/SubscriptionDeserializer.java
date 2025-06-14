package org.pub_sub.common.deserializers;

import org.apache.kafka.common.serialization.Deserializer;
import org.pub_sub.common.generated.SubscriptionProto;

public class SubscriptionDeserializer implements Deserializer<SubscriptionProto.Subscription> {

    @Override
    public SubscriptionProto.Subscription deserialize(String topic, byte[] data) {
        if (data == null) {
            return null;
        }
        try {
            return SubscriptionProto.Subscription.parseFrom(data);
        } catch (Exception e) {
            throw new RuntimeException("Failed to deserialize Subscription", e);
        }
    }

    @Override
    public void close() {}

}
