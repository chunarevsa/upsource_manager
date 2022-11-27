package com.rtkit.upsource_manager.entities.review;

// import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1
// import com.fasterxml.jackson.annotation.JsonProperty; // version 2.11.1
/* ObjectMapper om = new ObjectMapper();
Root root = om.readValue(myJsonString), Root.class); */

public class DiscussionCounter {
    public int count;
    public boolean hasUnresolved;
    public int unresolvedCount;
    public int resolvedCount;

    public int getCount() {
        return count;
    }

    public boolean isHasUnresolved() {
        return hasUnresolved;
    }

    public int getUnresolvedCount() {
        return unresolvedCount;
    }

    public int getResolvedCount() {
        return resolvedCount;
    }
}

