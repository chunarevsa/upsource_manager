package com.rtkit.upsource_manager.payload

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
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
@JsonIgnoreProperties(ignoreUnknown = true)
abstract class ABaseHTTPResponse() : IMappable {

    abstract override fun toString(): String

    var resultCode: Int = 0

    @JsonIgnore
    fun isNotSuccessful(): Boolean {
        return resultCode == 0
    }

    @JsonIgnore
    fun isSuccessful(): Boolean {
        return resultCode != 0
    }
}
