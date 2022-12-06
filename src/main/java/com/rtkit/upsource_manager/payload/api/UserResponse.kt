package com.rtkit.upsource_manager.payload.api

import com.rtkit.upsource_manager.payload.pacer.review.Participant
import com.rtkit.upsource_manager.payload.pacer.review.ParticipantRoot

class UserResponse(
    response: String
) : ABaseUpsourceResponse(response){

    private val participantRootObj = objectMapper.readValue(response, ParticipantRoot::class.java)

    fun getParticipants(): MutableList<Participant> {
        return participantRootObj.getResult().infos
    }

    fun getParticipant() : Participant {
        return participantRootObj.getResult().infos[0]
    }
}
