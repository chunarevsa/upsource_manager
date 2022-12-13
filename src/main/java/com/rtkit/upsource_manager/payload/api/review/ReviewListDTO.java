package com.rtkit.upsource_manager.payload.api.review;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rtkit.upsource_manager.payload.api.ABaseUpsourceResponse;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReviewListDTO extends ABaseUpsourceResponse {

    @JsonProperty("reviews")
    private List<Review> reviews = null;
    @JsonProperty("hasMore")
    private Boolean hasMore;
    @JsonProperty("totalCount")
    private Integer totalCount;

    /**
     * No args constructor for use in serialization
     */
    public ReviewListDTO() {
    }

    /**
     * @param reviews
     * @param hasMore
     * @param totalCount
     */
    public ReviewListDTO(List<Review> reviews, Boolean hasMore, Integer totalCount) {
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
