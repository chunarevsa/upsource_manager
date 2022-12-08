package com.rtkit.upsource_manager.repositories

import com.rtkit.upsource_manager.entities.participant.ParticipantEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ParticipantRepository : JpaRepository<ParticipantEntity, Long> {

}
