package com.rtkit.upsource_manager.payload.upsource.project;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rtkit.upsource_manager.payload.upsource.ABaseUpsourceResponse;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProjectInfoDTO extends ABaseUpsourceResponse {

    @JsonProperty("projectName")
    private String projectName;
    @JsonProperty("projectId")
    private String projectId;
    @JsonProperty("headHash")
    private String headHash;
    @JsonProperty("codeReviewIdPattern")
    private String codeReviewIdPattern;
    @JsonProperty("projectModelType")
    private String projectModelType;
    @JsonProperty("defaultEffectiveCharset")
    private String defaultEffectiveCharset;
    @JsonProperty("defaultBranch")
    private String defaultBranch;
    @JsonProperty("isConnectedToGithub")
    private Boolean isConnectedToGithub;
    @JsonProperty("isConnectedToGitlab")
    private Boolean isConnectedToGitlab;
    @JsonProperty("iconUrl")
    private String iconUrl;
    @JsonProperty("group")
    private Group group;
    @JsonProperty("isArchived")
    private Boolean isArchived;

    /**
     * No args constructor for use in serialization
     */
    public ProjectInfoDTO() {
    }

    /**
     * @param projectModelType
     * @param isArchived
     * @param headHash
     * @param defaultEffectiveCharset
     * @param defaultBranch
     * @param isConnectedToGithub
     * @param iconUrl
     * @param projectName
     * @param projectId
     * @param codeReviewIdPattern
     * @param isConnectedToGitlab
     * @param group
     */
    public ProjectInfoDTO(String projectName, String projectId, String headHash, String codeReviewIdPattern, String projectModelType, String defaultEffectiveCharset, String defaultBranch, Boolean isConnectedToGithub, Boolean isConnectedToGitlab, String iconUrl, Group group, Boolean isArchived) {
        super();
        this.projectName = projectName;
        this.projectId = projectId;
        this.headHash = headHash;
        this.codeReviewIdPattern = codeReviewIdPattern;
        this.projectModelType = projectModelType;
        this.defaultEffectiveCharset = defaultEffectiveCharset;
        this.defaultBranch = defaultBranch;
        this.isConnectedToGithub = isConnectedToGithub;
        this.isConnectedToGitlab = isConnectedToGitlab;
        this.iconUrl = iconUrl;
        this.group = group;
        this.isArchived = isArchived;
    }

    @JsonProperty("projectName")
    public String getProjectName() {
        return projectName;
    }

    @JsonProperty("projectName")
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @JsonProperty("projectId")
    public String getProjectId() {
        return projectId;
    }

    @JsonProperty("projectId")
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    @JsonProperty("headHash")
    public String getHeadHash() {
        return headHash;
    }

    @JsonProperty("headHash")
    public void setHeadHash(String headHash) {
        this.headHash = headHash;
    }

    @JsonProperty("codeReviewIdPattern")
    public String getCodeReviewIdPattern() {
        return codeReviewIdPattern;
    }

    @JsonProperty("codeReviewIdPattern")
    public void setCodeReviewIdPattern(String codeReviewIdPattern) {
        this.codeReviewIdPattern = codeReviewIdPattern;
    }

    @JsonProperty("projectModelType")
    public String getProjectModelType() {
        return projectModelType;
    }

    @JsonProperty("projectModelType")
    public void setProjectModelType(String projectModelType) {
        this.projectModelType = projectModelType;
    }

    @JsonProperty("defaultEffectiveCharset")
    public String getDefaultEffectiveCharset() {
        return defaultEffectiveCharset;
    }

    @JsonProperty("defaultEffectiveCharset")
    public void setDefaultEffectiveCharset(String defaultEffectiveCharset) {
        this.defaultEffectiveCharset = defaultEffectiveCharset;
    }

    @JsonProperty("defaultBranch")
    public String getDefaultBranch() {
        return defaultBranch;
    }

    @JsonProperty("defaultBranch")
    public void setDefaultBranch(String defaultBranch) {
        this.defaultBranch = defaultBranch;
    }

    @JsonProperty("isConnectedToGithub")
    public Boolean getIsConnectedToGithub() {
        return isConnectedToGithub;
    }

    @JsonProperty("isConnectedToGithub")
    public void setIsConnectedToGithub(Boolean isConnectedToGithub) {
        this.isConnectedToGithub = isConnectedToGithub;
    }

    @JsonProperty("isConnectedToGitlab")
    public Boolean getIsConnectedToGitlab() {
        return isConnectedToGitlab;
    }

    @JsonProperty("isConnectedToGitlab")
    public void setIsConnectedToGitlab(Boolean isConnectedToGitlab) {
        this.isConnectedToGitlab = isConnectedToGitlab;
    }

    @JsonProperty("iconUrl")
    public String getIconUrl() {
        return iconUrl;
    }

    @JsonProperty("iconUrl")
    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    @JsonProperty("group")
    public Group getGroup() {
        return group;
    }

    @JsonProperty("group")
    public void setGroup(Group group) {
        this.group = group;
    }

    @JsonProperty("isArchived")
    public Boolean getIsArchived() {
        return isArchived;
    }

    @JsonProperty("isArchived")
    public void setIsArchived(Boolean isArchived) {
        this.isArchived = isArchived;
    }

    @Override
    public String toString() {
        return "ProjectInfoDTO{" +
                "projectName='" + projectName + '\'' +
                ", projectId='" + projectId + '\'' +
                ", headHash='" + headHash + '\'' +
                ", codeReviewIdPattern='" + codeReviewIdPattern + '\'' +
                ", projectModelType='" + projectModelType + '\'' +
                ", defaultEffectiveCharset='" + defaultEffectiveCharset + '\'' +
                ", defaultBranch='" + defaultBranch + '\'' +
                ", isConnectedToGithub=" + isConnectedToGithub +
                ", isConnectedToGitlab=" + isConnectedToGitlab +
                ", iconUrl='" + iconUrl + '\'' +
                ", group=" + group +
                ", isArchived=" + isArchived +
                '}';
    }
}
