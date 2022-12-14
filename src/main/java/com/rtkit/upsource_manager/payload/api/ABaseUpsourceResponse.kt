package com.rtkit.upsource_manager.payload.api

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonRootName

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
abstract class ABaseUpsourceResponse : IMappable {

    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var resultCode: Int = 0

    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var isSuccessful: Boolean = false

    fun isSuccessfull(): Boolean {
        return resultCode != 0
    }

}
