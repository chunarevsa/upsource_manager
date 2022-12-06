package com.rtkit.upsource_manager.payload.api

import com.fasterxml.jackson.databind.ObjectMapper

/**
 * Абстрактный ответ от UPSOURCE
 */
abstract class ABaseUpsourceResponse(
    var result: String
) {
    val objectMapper: ObjectMapper = ObjectMapper()
}
