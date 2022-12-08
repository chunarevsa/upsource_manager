package com.rtkit.upsource_manager.services

import com.rtkit.upsource_manager.entities.participant.ParticipantEntity
import com.rtkit.upsource_manager.entities.review.ReviewEntity
import com.rtkit.upsource_manager.payload.pacer.review.Participant
import com.rtkit.upsource_manager.payload.pacer.review.Review
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

    fun findNewParticipants(onlyUpdatedReviews: MutableSet<ReviewEntity>): MutableSet<ParticipantEntity> {
        val participantsFromDB = getAllParticipants()

        val participantsFromRequest: MutableSet<ParticipantEntity> = mutableSetOf()
        onlyUpdatedReviews.forEach { review ->
            review.participants.forEach { participant ->
                participantsFromRequest.add(participant)
            }
        }

        logger.info("participants from request: $participantsFromRequest")
        logger.info("participants from db: $participantsFromDB")

        return mutableSetOf() // TODO: убрать

    }

//
//    val participants: MutableSet<Participant> = mutableSetOf()
//    reviews.forEach { review: Review ->
//        review.participants.forEach { participant: Participant ->
//            participants.add(participant)
//        }
//    }
//
//
//    review.participants.forEach(Consumer { participant: Participant? ->
//        participants.add(
//            ParticipantEntity(participant)
//        )
//    })


    private fun getAllParticipants(): MutableSet<ParticipantEntity> {
        return participantRepository.findAll().toMutableSet()
    }

    fun saveParticipants(participants: MutableSet<ParticipantEntity>) {
        participants.stream().map { participant -> saveParticipant(participant) }.collect(Collectors.toSet())
    }

    private fun saveParticipant(participantEntity: ParticipantEntity): ParticipantEntity {
        return participantRepository.save(participantEntity)
    }

    fun getParticipantsFromReviews(reviewsFromRequest: MutableList<Review>): MutableSet<Participant> {
        return mutableSetOf() // TODO: убрать
    }

    fun getParticipantEntitiesByParticipants(participants: MutableSet<Participant>): MutableSet<ParticipantEntity> {

        return mutableSetOf() // TODO: убрать
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
