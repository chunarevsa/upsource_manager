
package com.rtkit.upsource_manager.payload.api.response.reviewList;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "completedCount",
    "reviewersCount",
    "hasConcern"
})
@Generated("jsonschema2pojo")
public class CompletionRate {

    @JsonProperty("completedCount")
    private Integer completedCount;
    @JsonProperty("reviewersCount")
    private Integer reviewersCount;
    @JsonProperty("hasConcern")
    private Boolean hasConcern;

    /**
     * No args constructor for use in serialization
     * 
     */
    public CompletionRate() {
    }

    /**
     * 
     * @param hasConcern
     * @param completedCount
     * @param reviewersCount
     */
    public CompletionRate(Integer completedCount, Integer reviewersCount, Boolean hasConcern) {
        super();
        this.completedCount = completedCount;
        this.reviewersCount = reviewersCount;
        this.hasConcern = hasConcern;
    }

    @JsonProperty("completedCount")
    public Integer getCompletedCount() {
        return completedCount;
    }

    @JsonProperty("completedCount")
    public void setCompletedCount(Integer completedCount) {
        this.completedCount = completedCount;
    }

    @JsonProperty("reviewersCount")
    public Integer getReviewersCount() {
        return reviewersCount;
    }

    @JsonProperty("reviewersCount")
    public void setReviewersCount(Integer reviewersCount) {
        this.reviewersCount = reviewersCount;
    }

    @JsonProperty("hasConcern")
    public Boolean getHasConcern() {
        return hasConcern;
    }

    @JsonProperty("hasConcern")
    public void setHasConcern(Boolean hasConcern) {
        this.hasConcern = hasConcern;
    }

}
