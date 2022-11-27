package com.rtkit.upsource_manager.entities.review;

// import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1
// import com.fasterxml.jackson.annotation.JsonProperty; // version 2.11.1
/* ObjectMapper om = new ObjectMapper();
Root root = om.readValue(myJsonString), Root.class); */

public class CompletionRate {
    public int completedCount;
    public int reviewersCount;
    public boolean hasConcern;

    public int getCompletedCount() {
        return completedCount;
    }

    public int getReviewersCount() {
        return reviewersCount;
    }

    public boolean isHasConcern() {
        return hasConcern;
    }
}

