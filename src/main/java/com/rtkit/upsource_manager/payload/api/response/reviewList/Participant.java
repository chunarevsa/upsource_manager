package com.rtkit.upsource_manager.payload.api.response.reviewList;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Participant {

    @JsonProperty("userId")
    private String userId;
    @JsonProperty("role")
    private Integer role;
    @JsonProperty("state")
    private Integer state;

    /**
     * No args constructor for use in serialization
     */
    public Participant() {
    }

    /**
     * @param role
     * @param state
     * @param userId
     */
    public Participant(String userId, Integer role, Integer state) {
        super();
        this.userId = userId;
        this.role = role;
        this.state = state;
    }

    @JsonProperty("userId")
    public String getUserId() {
        return userId;
    }

    @JsonProperty("userId")
    public void setUserId(String userId) {
        this.userId = userId;
    }

    @JsonProperty("role")
    public Integer getRole() {
        return role;
    }

    @JsonProperty("role")
    public void setRole(Integer role) {
        this.role = role;
    }

    @JsonProperty("state")
    public Integer getState() {
        return state;
    }

    @JsonProperty("state")
    public void setState(Integer state) {
        this.state = state;
    }

}
