package com.rtkit.upsource_manager.payload.api;

import java.io.Serializable;
import java.net.ProtocolException;

public interface IMapper extends Serializable {
    /**
     * Сереализация обьекта.
     *
     * @param value Сериализуемый объект
     *
     * @return Строковое представление объекта
     */
    String writeValueAsString(IMappable value) throws ProtocolException;

    default String writeValueAsString(Object value) throws ProtocolException {
        return "";
    }

    /**
     * Десерелизация
     *
     * @param content   Строковое представление объекта
     * @param valueType Класс объекта
     *
     * @return Объект заданного класса
     */
    <T extends IMappable> T readValue(String content, Class<T> valueType) throws ProtocolException;


}
