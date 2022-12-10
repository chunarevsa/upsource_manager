
package com.rtkit.upsource_manager.payload.api.response.reviewList;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "count",
    "hasUnresolved",
    "unresolvedCount",
    "resolvedCount"
})
@Generated("jsonschema2pojo")
public class DiscussionCounter {

    @JsonProperty("count")
    private Integer count;
    @JsonProperty("hasUnresolved")
    private Boolean hasUnresolved;
    @JsonProperty("unresolvedCount")
    private Integer unresolvedCount;
    @JsonProperty("resolvedCount")
    private Integer resolvedCount;

    /**
     * No args constructor for use in serialization
     * 
     */
    public DiscussionCounter() {
    }

    /**
     * 
     * @param unresolvedCount
     * @param hasUnresolved
     * @param count
     * @param resolvedCount
     */
    public DiscussionCounter(Integer count, Boolean hasUnresolved, Integer unresolvedCount, Integer resolvedCount) {
        super();
        this.count = count;
        this.hasUnresolved = hasUnresolved;
        this.unresolvedCount = unresolvedCount;
        this.resolvedCount = resolvedCount;
    }

    @JsonProperty("count")
    public Integer getCount() {
        return count;
    }

    @JsonProperty("count")
    public void setCount(Integer count) {
        this.count = count;
    }

    @JsonProperty("hasUnresolved")
    public Boolean getHasUnresolved() {
        return hasUnresolved;
    }

    @JsonProperty("hasUnresolved")
    public void setHasUnresolved(Boolean hasUnresolved) {
        this.hasUnresolved = hasUnresolved;
    }

    @JsonProperty("unresolvedCount")
    public Integer getUnresolvedCount() {
        return unresolvedCount;
    }

    @JsonProperty("unresolvedCount")
    public void setUnresolvedCount(Integer unresolvedCount) {
        this.unresolvedCount = unresolvedCount;
    }

    @JsonProperty("resolvedCount")
    public Integer getResolvedCount() {
        return resolvedCount;
    }

    @JsonProperty("resolvedCount")
    public void setResolvedCount(Integer resolvedCount) {
        this.resolvedCount = resolvedCount;
    }

}
