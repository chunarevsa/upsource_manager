package com.rtkit.upsource_manager.payload.upsource.review

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.rtkit.upsource_manager.payload.upsource.ABaseUpsourceResponse

@JsonInclude(JsonInclude.Include.NON_NULL)
class ReviewDescriptorDTO() : ABaseUpsourceResponse() {

    @JsonProperty("reviewId")
    var reviewId: ReviewId? = null

    @JsonProperty("title")
    var title: String? = null

    @JsonProperty("description")
    var description: String? = null

    @JsonProperty("participants")
    var participants: List<Participant> = mutableListOf()

    @JsonProperty("state")
    var state: Int? = null

    @JsonProperty("isUnread")
    var isUnread: Boolean? = null

    @JsonProperty("isReadyToClose")
    var isReadyToClose: Boolean? = null

    @JsonProperty("branch")
    var branch: List<String>? = null

    @JsonProperty("isRemoved")
    var isRemoved: Boolean? = null

    @JsonProperty("createdAt")
    var createdAt: Long? = null

    @JsonProperty("createdBy")
    var createdBy: String? = null

    @JsonProperty("updatedAt")
    var updatedAt: Long? = null

    @JsonProperty("completionRate")
    var completionRate: CompletionRate? = null

    @JsonProperty("discussionCounter")
    var discussionCounter: DiscussionCounter? = null

    @JsonProperty("deadline")
    var deadline: Long? = null

    @JsonProperty("isMuted")
    var isMuted: Boolean? = null

    @JsonProperty("mergeFromBranch")
    var mergeFromBranch: String? = null

    @JsonProperty("mergeToBranch")
    var mergeToBranch: String? = null

    override fun toString(): String {
        return "ReviewDescriptorDTO(" +
                "reviewId=$reviewId, " +
                "title=$title, " +
                "description=$description, " +
                "participants=$participants, " +
                "state=$state, " +
                "isUnread=$isUnread, " +
                "isReadyToClose=$isReadyToClose, " +
                "branch=$branch, " +
                "isRemoved=$isRemoved, " +
                "createdAt=$createdAt, " +
                "createdBy=$createdBy, " +
                "updatedAt=$updatedAt, " +
                "completionRate=$completionRate, " +
                "discussionCounter=$discussionCounter, " +
                "deadline=$deadline, " +
                "isMuted=$isMuted, " +
                "mergeFromBranch=$mergeFromBranch, " +
                "mergeToBranch=$mergeToBranch)"
    }
}
