package com.rtkit.upsource_manager.events.upsource
//
//import com.rtkit.upsource_manager.events.bot.BotInstance
//import com.rtkit.upsource_manager.payload.upsource.review.Review
//import org.springframework.stereotype.Component
//
///**
// * Событие: нашли протухшее ревью, оповещаем пользователя о закрытии и добавляем в статистику
// */
////@Component
//class FindExpiredReviewListener(
//    private val botInstance: BotInstance
//) : AUpsourceListener<FindExpiredReview>() {
//
//    override fun onApplicationEvent(event: FindExpiredReview) {
//        logger.info("========== Нашли просроченное ревью: ${event.review.reviewId}")
////        botService.notifyUser()
//        botInstance.logger.info("!!!!!!!!!!!!")
//    }
//}
//
//class FindExpiredReview(
//    var review: Review
//) : AUpsourceEvent(review)

