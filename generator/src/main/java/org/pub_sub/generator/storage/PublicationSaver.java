package org.pub_sub.generator.storage;

import org.pub_sub.generator.Publication;

import java.io.IOException;

public interface PublicationSaver {
    void save(Publication publication) throws Exception;
    void close() throws IOException;
}

