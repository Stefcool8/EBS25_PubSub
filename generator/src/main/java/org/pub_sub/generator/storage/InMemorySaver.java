package org.pub_sub.generator.storage;

import org.pub_sub.generator.Publication;

import java.util.ArrayList;
import java.util.List;

public class InMemorySaver implements PublicationSaver {
    private final List<Publication> publications = new ArrayList<>();

    @Override
    public void save(Publication publication) {
        publications.add(publication);
    }

    @Override
    public void close(){
        // No resources to close in memory saver
    }

    public List<Publication> getPublications() {
        return publications;
    }
}
