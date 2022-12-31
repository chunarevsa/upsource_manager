package com.rtkit.upsource_manager.payload.upsource

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonRootName
import com.rtkit.upsource_manager.payload.ABaseHTTPResponse

/**
 * Абстрактный ответ от UPSOURCE
 */
@JsonAutoDetect(
    creatorVisibility = JsonAutoDetect.Visibility.NONE,
    fieldVisibility = JsonAutoDetect.Visibility.NONE,
    getterVisibility = JsonAutoDetect.Visibility.NONE,
    isGetterVisibility = JsonAutoDetect.Visibility.NONE,
    setterVisibility = JsonAutoDetect.Visibility.NONE
)
// От upsource всегда приходит рут {"result": {...}}
@JsonRootName(value = "result")
@JsonIgnoreProperties(ignoreUnknown = true)
abstract class ABaseUpsourceResponse() : ABaseHTTPResponse()
