package com.rtkit.upsource_manager.payload.api.response.userInfo;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "userId",
        "name",
        "isResolved",
        "isMe",
        "avatarUrl",
        "email",
        "profileUrl",
        "login"
})
@Generated("jsonschema2pojo")
public class Info {

    @JsonProperty("userId")
    private String userId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("isResolved")
    private Boolean isResolved;
    @JsonProperty("isMe")
    private Boolean isMe;
    @JsonProperty("avatarUrl")
    private String avatarUrl;
    @JsonProperty("email")
    private String email;
    @JsonProperty("profileUrl")
    private String profileUrl;
    @JsonProperty("login")
    private String login;


    /**
     * No args constructor for use in serialization
     *
     */
    public Info() {
    }

    /**
     *
     * @param isMe
     * @param avatarUrl
     * @param name
     * @param userId
     * @param isResolved
     * @param email
     */
    public Info(String userId, String name, Boolean isResolved, Boolean isMe, String avatarUrl, String email, String profileUrl, String login) {
        super();
        this.userId = userId;
        this.name = name;
        this.isResolved = isResolved;
        this.isMe = isMe;
        this.avatarUrl = avatarUrl;
        this.email = email;
        this.profileUrl = profileUrl;
        this.login = login;
    }

    @JsonProperty("userId")
    public String getUserId() {
        return userId;
    }

    @JsonProperty("userId")
    public void setUserId(String userId) {
        this.userId = userId;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("isResolved")
    public Boolean getIsResolved() {
        return isResolved;
    }

    @JsonProperty("isResolved")
    public void setIsResolved(Boolean isResolved) {
        this.isResolved = isResolved;
    }

    @JsonProperty("isMe")
    public Boolean getIsMe() {
        return isMe;
    }

    @JsonProperty("isMe")
    public void setIsMe(Boolean isMe) {
        this.isMe = isMe;
    }

    @JsonProperty("avatarUrl")
    public String getAvatarUrl() {
        return avatarUrl;
    }

    @JsonProperty("avatarUrl")
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getResolved() {
        return isResolved;
    }

    public void setResolved(Boolean resolved) {
        isResolved = resolved;
    }

    public Boolean getMe() {
        return isMe;
    }

    public void setMe(Boolean me) {
        isMe = me;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}