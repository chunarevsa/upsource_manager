package com.rtkit.upsource_manager.payload.upsource.revision;

import java.util.List;

public class AllRevisions {
    public List<Revision> revision;
    public Graph graph;

    public List<Revision> getRevision() {
        return revision;
    }

    public Graph getGraph() {
        return graph;
    }
}
