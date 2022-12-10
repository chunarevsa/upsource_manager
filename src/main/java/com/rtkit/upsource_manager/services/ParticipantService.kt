package com.rtkit.upsource_manager.services

import com.rtkit.upsource_manager.entities.participant.ParticipantEntity
import com.rtkit.upsource_manager.repositories.ParticipantRepository
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class ParticipantService(
    private val participantRepository: ParticipantRepository
) {
    private val logger: Logger = LogManager.getLogger(ParticipantService::class.java)

    private fun getAllParticipants(): MutableSet<ParticipantEntity> {
        return participantRepository.findAll().toMutableSet()
    }

    fun saveParticipants(participants: MutableSet<ParticipantEntity>) {
        participants.stream().map { participant -> saveParticipant(participant) }.collect(Collectors.toSet())
    }

    private fun saveParticipant(participantEntity: ParticipantEntity): ParticipantEntity {
        return participantRepository.save(participantEntity)
    }


//    private fun findUsernameById(userId: String): String? {
//        if (participants[userId] != null) return participants[userId].toString()
//
//        val participant = protocolService.makeRequest(FullUserInfoDTO(userId)).getFirstParticipant()
//
//        participants[userId] = participant.name
//        return participants[userId]
//    }
}
