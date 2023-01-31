## Upsource Manager ##
#### Автор: Чунарёв Сергей. ####

## Описание ##

Менеджер для более эффективного ревью кода в команде.

Состоит из двух частей: 
- __Сервис взаимодействия с Upsource Api__
- __Discord bot__
## Сервис взаимодействия с Upsource Api ##
- Получает список ревью в проекте 
- Получает список разработчиков
- Проставляет статут в зависимости от даты deadline
- Можно настроить расписание запросов
- Находит и закрывает "Пустые ревью" (c пустыми Revision)
- Отправляет список ревью в Discrod bot после апдейта
- При подписке пользователя делает апдейт ревью

## Discord bot ##
- Показывает ИНТРО сообщение
- Показывает актуальный список ревью конкретного пользователя.
- При апдейте изменяет текущие сообщения (не трогает ИНТРО)
- Можно привязать Discord пользователя к Upsource пользователю
- Выравнивание (не полное) блоков сообщения

##### Слеш-команды в порядке использования
- `/upsource-channel-init` - Инициализация канала  
- `/add-upsource-user` - Привязка Discord пользователя к Upsource пользователю
- `/init-user` - Подписка пользователя на получение ревью

Изначально предполагается, что первые две команды будет выполнять администратор, 
поэтому в ИНТРО подсказка только последней команды

## Технологии ##
* Kotlin 1.7.22
* Java 8
* Spring boot 2.5.1

## Библиотека ##
* [JDA 5.0.0-alpha.13](https://github.com/DV8FromTheWorld/JDA)


## Запуск ## 
("*Стрекотание сверчков*")

## Планы по разработке ##
* ExceptionHandler
* Админский канал со статистикой
* Пересмотреть логирование 
* Уведомление пользователей по расписанию (пн-пт) 
* Уведомление по расписанию (раз в час?) о совсем горящих ревью
