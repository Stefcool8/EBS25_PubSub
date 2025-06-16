package org.pub_sub.generator.storage;

import org.pub_sub.generator.Publication;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class TextFilePublicationSaver implements PublicationSaver {
    private final BufferedWriter writer;
    private boolean firstWrite = true;

    public TextFilePublicationSaver(String fileName) throws IOException {
        writer = new BufferedWriter(new FileWriter(fileName));
    }

    @Override
    public synchronized void save(Publication publication) throws IOException {
        if (!firstWrite) {
            writer.write("\n");
        }
        writer.write(publication.toJson());
        firstWrite = false;
    }

    @Override
    public void close() throws IOException {
        writer.close();
    }
}
