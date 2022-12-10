package com.rtkit.upsource_manager.payload.api.response.reviewList;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "reviews",
        "hasMore",
        "totalCount"
})
@Generated("jsonschema2pojo")
public class Result {

    @JsonProperty("reviews")
    private List<Review> reviews = null;
    @JsonProperty("hasMore")
    private Boolean hasMore;
    @JsonProperty("totalCount")
    private Integer totalCount;

    /**
     * No args constructor for use in serialization
     */
    public Result() {
    }

    /**
     * @param reviews
     * @param hasMore
     * @param totalCount
     */
    public Result(List<Review> reviews, Boolean hasMore, Integer totalCount) {
        super();
        this.reviews = reviews;
        this.hasMore = hasMore;
        this.totalCount = totalCount;
    }

    @JsonProperty("reviews")
    public List<Review> getReviews() {
        return reviews;
    }

    @JsonProperty("reviews")
    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    @JsonProperty("hasMore")
    public Boolean getHasMore() {
        return hasMore;
    }

    @JsonProperty("hasMore")
    public void setHasMore(Boolean hasMore) {
        this.hasMore = hasMore;
    }

    @JsonProperty("totalCount")
    public Integer getTotalCount() {
        return totalCount;
    }

    @JsonProperty("totalCount")
    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

}
