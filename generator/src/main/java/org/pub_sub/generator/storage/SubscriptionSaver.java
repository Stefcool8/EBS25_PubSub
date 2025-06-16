package org.pub_sub.generator.storage;

import org.pub_sub.generator.Subscription;

import java.io.IOException;

public interface SubscriptionSaver {
    void save(Subscription subscription) throws IOException;
    void close() throws IOException;
}
