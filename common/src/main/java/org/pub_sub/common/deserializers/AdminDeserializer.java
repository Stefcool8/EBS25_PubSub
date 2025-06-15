package org.pub_sub.common.deserializers;

import org.apache.kafka.common.serialization.Deserializer;
import org.pub_sub.common.generated.AdminProto;

public class AdminDeserializer implements Deserializer<AdminProto.AdminMessage> {

    @Override
    public AdminProto.AdminMessage deserialize(String topic, byte[] data) {
        if (data == null || data.length == 0) return null;
        try {
            return AdminProto.AdminMessage.parseFrom(data);
        } catch (Exception e) {
            throw new RuntimeException("Failed to deserialize AdminMessage", e);
        }
    }

    @Override
    public void close() {
    }
}
