package com.rtkit.upsource_manager.repositories;

import com.rtkit.upsource_manager.entities.review.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {

    void deleteByUpsourceId(String upsourceId);

    ReviewEntity findByUpsourceId(String upsourceId);
}
