package com.rtkit.upsource_manager.payload.api.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Info {

    @JsonProperty("userId")
    private String userId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("isResolved")
    private Boolean isResolved;
    @JsonProperty("isMe")
    private Boolean isMe;
    @JsonProperty("isOnline")
    private Boolean isOnline;
    @JsonProperty("avatarUrl")
    private String avatarUrl;
    @JsonProperty("profileUrl")
    private String profileUrl;
    @JsonProperty("email")
    private String email;
    @JsonProperty("login")
    private String login;


    /**
     * No args constructor for use in serialization
     */
    public Info() {
    }

    /**
     * @param userId
     * @param name
     * @param isResolved
     * @param isMe
     * @param isOnline
     * @param avatarUrl
     * @param profileUrl
     * @param email
     * @param login
     */
    public Info(String userId, String name, Boolean isResolved, Boolean isMe, Boolean isOnline, String avatarUrl, String profileUrl, String email, String login) {
        this.userId = userId;
        this.name = name;
        this.isResolved = isResolved;
        this.isMe = isMe;
        this.isOnline = isOnline;
        this.avatarUrl = avatarUrl;
        this.profileUrl = profileUrl;
        this.email = email;
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

    public Boolean getOnline() {
        return isOnline;
    }

    public void setOnline(Boolean online) {
        isOnline = online;
    }
}