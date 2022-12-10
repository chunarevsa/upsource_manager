package com.rtkit.upsource_manager.payload.api.response.reviewList;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.rtkit.upsource_manager.payload.api.response.ABaseUpsourceResponse;

import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "result"
})
@Generated("jsonschema2pojo")
public class ReviewListDTO extends ABaseUpsourceResponse {

    @JsonProperty("result")
    private Result result;

    /**
     * No args constructor for use in serialization
     */
    public ReviewListDTO() {
    }

    /**
     * @param result
     */
    public ReviewListDTO(Result result) {
        super();
        this.result = result;
    }

    @JsonProperty("result")
    public Result getResult() {
        return result;
    }

    @JsonProperty("result")
    public void setResult(Result result) {
        this.result = result;
    }

}
