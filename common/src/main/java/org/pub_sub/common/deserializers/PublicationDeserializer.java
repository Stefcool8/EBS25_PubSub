package org.pub_sub.common.deserializers;


import org.apache.kafka.common.serialization.Deserializer;
import org.pub_sub.common.generated.PublicationProto;

public class PublicationDeserializer implements Deserializer<PublicationProto.Publication> {
    @Override
    public PublicationProto.Publication deserialize(String s, byte[] data) {
        if (data == null || data.length == 0) return null;
        try {
            return PublicationProto.Publication.parseFrom(data);
        } catch (Exception e) {
            throw new RuntimeException("Failed to deserialize Publication", e);
        }
    }

    @Override
    public void close() {}
}
