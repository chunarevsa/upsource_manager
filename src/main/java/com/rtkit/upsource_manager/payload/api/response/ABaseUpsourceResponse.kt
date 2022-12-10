package com.rtkit.upsource_manager.payload.api.response

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.rtkit.upsource_manager.payload.api.IMappable

/**
 * Абстрактный ответ от UPSOURCE
 */
@JsonAutoDetect(
    creatorVisibility = JsonAutoDetect.Visibility.NONE,
    fieldVisibility = JsonAutoDetect.Visibility.NONE,
    getterVisibility = JsonAutoDetect.Visibility.NONE,
    isGetterVisibility = JsonAutoDetect.Visibility.NONE,
    setterVisibility = JsonAutoDetect.Visibility.NONE
) // Игнорируем поля родителя, который у нас в библиотеке...


abstract class ABaseUpsourceResponse : IMappable {

}
