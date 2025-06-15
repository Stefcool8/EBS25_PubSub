package org.pub_sub.common.deserializers;

import org.apache.kafka.common.serialization.Deserializer;
import org.pub_sub.common.generated.ForwardProto;

public class ForwardDeserializer implements Deserializer<ForwardProto.ForwardMessage> {
    @Override
    public ForwardProto.ForwardMessage deserialize(String topic, byte[] data) {
        if (data == null || data.length == 0) return null;
        try {
            return ForwardProto.ForwardMessage.parseFrom(data);
        } catch (Exception e) {
            throw new RuntimeException("Failed to deserialize ForwardMessage", e);
        }
    }

    @Override
    public void close() {
    }
}
