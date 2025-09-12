# Документация API SkillFitBox

Этот документ содержит информацию об API системы управления фитнес-центром SkillFitBox, включая инструкции по использованию Swagger UI и коллекции Postman.

## Обзор API

API SkillFitBox предоставляет эндпоинты для управления:
- **Клиентами**: Создание, чтение, обновление и управление клиентами фитнес-центра
- **Тренерами**: Управление фитнес-тренерами и их статусом
- **Шкафчиками**: Просмотр назначений шкафчиков и их доступности
- **Услугами**: Управление дополнительными услугами (сауна, бассейн и т.д.)

## Базовый URL

- **Разработка**: `http://localhost:8090/api`
- **Продакшн**: `https://api.skillfitbox.com`

**Примечание**: Приложение запускается на порту 8090 с контекстным путем `/api`, поэтому все эндпоинты доступны по адресу `http://localhost:8090/api/...`

## Swagger UI

### Доступ к Swagger UI

После запуска приложения вы можете получить доступ к Swagger UI по адресу:
- **URL**: `http://localhost:8090/api/swagger-ui.html`

### Возможности

- **Интерактивная документация API**: Просмотр всех доступных эндпоинтов
- **Попробовать**: Тестирование эндпоинтов API непосредственно из браузера
- **Примеры запросов/ответов**: Просмотр примеров запросов и ответов
- **Документация схем**: Просмотр детальных моделей данных
- **Аутентификация**: Настройка аутентификации при необходимости

### Использование Swagger UI

1. Запустите приложение: `mvn spring-boot:run`
2. Откройте браузер и перейдите по адресу `http://localhost:8090/api/swagger-ui.html`
3. Изучите документацию API, организованную по тегам:
   - Управление клиентами
   - Управление тренерами
   - Управление шкафчиками
   - Управление услугами
4. Нажмите на любой эндпоинт, чтобы увидеть детали и попробовать его
5. Используйте кнопку "Try it out" для тестирования эндпоинтов с примерными данными

## Коллекция Postman

### Импорт коллекции

1. Откройте Postman
2. Нажмите кнопку "Import"
3. Выберите файл `SkillFitBox_API_Collection.postman_collection.json`
4. Импортируйте файл окружения `SkillFitBox_Environment.postman_environment.json`

### Переменные окружения

Коллекция использует следующие переменные окружения:

| Переменная | Значение по умолчанию | Описание |
|------------|----------------------|----------|
| `base_url` | `http://localhost:8090/api` | Базовый URL для API |
| `client_id` | (пустое) | ID клиента для тестирования |
| `trainer_id` | (пустое) | ID тренера для тестирования |
| `locker_id` | (пустое) | ID шкафчика для тестирования |
| `service_id` | `SOLARIUM` | ID услуги для тестирования |

### Доступные услуги

В системе доступны следующие услуги:
- `SOLARIUM` - Солярий
- `POOL` - Бассейн
- `SAUNA` - Сауна
- `CRYOSAUNA` - Криосауна
- `CROSSFIT` - Кроссфит

### Статусы тренеров

Доступные статусы тренеров:
- `WORKING` - Работает
- `ON_LEAVE` - В отпуске
- `NOT_WORKING` - Не работает

### Использование коллекции

1. **Настройка окружения**: Убедитесь, что выбрано окружение SkillFitBox
2. **Создание тестовых данных**: Используйте папку "Sample Data Setup" для создания примерных клиентов и тренеров
3. **Тестирование эндпоинтов**: Используйте организованные папки для тестирования различных эндпоинтов API
4. **Обновление переменных**: После создания клиентов/тренеров обновите переменные окружения возвращенными ID

### Пример рабочего процесса

1. **Создание клиента**:
   ```
   POST /api/clients
   {
     "surname": "Иванов",
     "name": "Иван",
     "patronymic": "Иванович",
     "birthday": "1990-05-15",
     "phone": "+79123456789",
     "email": "ivan.ivanov@example.com",
     "isActive": true
   }
   ```

2. **Создание тренера**:
   ```
   POST /api/trainers
   {
     "surname": "Петров",
     "name": "Петр",
     "patronymic": "Петрович",
     "phone": "+79123456790",
     "status": "WORKING"
   }
   ```

3. **Назначение тренера клиенту**:
   ```
   POST /api/clients/{client_id}/trainer/{trainer_id}
   ```

4. **Добавление услуги клиенту**:
   ```
   POST /api/clients/{client_id}/services/SOLARIUM
   ```

5. **Назначение шкафчика клиенту**:
   ```
   POST /api/clients/{client_id}/locker/{locker_id}
   ```

## Сводка эндпоинтов API

### Управление клиентами
- `POST /clients` - Создать нового клиента
- `GET /clients` - Получить всех клиентов
- `GET /clients/{id}` - Получить клиента по ID
- `GET /clients/{id}/detail` - Получить детальную информацию о клиенте
- `PUT /clients/{id}` - Обновить информацию о клиенте
- `PATCH /clients/{id}/status` - Обновить статус клиента
- `POST /clients/{clientId}/trainer/{trainerId}` - Назначить тренера клиенту
- `POST /clients/{clientId}/services/{serviceId}` - Добавить услугу клиенту
- `POST /clients/{clientId}/locker/{lockerId}` - Назначить шкафчик клиенту

### Управление тренерами
- `POST /trainers` - Создать нового тренера
- `GET /trainers` - Получить всех тренеров
- `GET /trainers/{id}/detail` - Получить детальную информацию о тренере
- `PUT /trainers/{id}` - Обновить информацию о тренере
- `PATCH /trainers/{id}/status` - Изменить статус тренера

### Управление шкафчиками
- `GET /lockers` - Получить все шкафчики с информацией о клиентах

### Управление услугами
- `GET /services` - Получить все доступные услуги
- `GET /services/{id}` - Получить услугу по ID с информацией о клиентах

## Модели данных

### ClientDto
```json
{
  "id": "UUID",
  "surname": "string",
  "name": "string",
  "patronymic": "string",
  "birthday": "date (YYYY-MM-DD)",
  "phone": "string",
  "email": "string (email format)",
  "isActive": "boolean"
}
```

### ClientDetailDto
```json
{
  "id": "UUID",
  "surname": "string",
  "name": "string",
  "patronymic": "string",
  "birthday": "date (YYYY-MM-DD)",
  "phone": "string",
  "email": "string (email format)",
  "isActive": "boolean",
  "locker": {
    "id": "UUID",
    "number": "integer",
    "clientFullName": "string"
  },
  "trainer": {
    "id": "UUID",
    "surname": "string",
    "name": "string",
    "patronymic": "string",
    "phone": "string",
    "status": "WORKING|ON_LEAVE|NOT_WORKING"
  },
  "services": ["string"]
}
```

### TrainerDto
```json
{
  "id": "UUID",
  "surname": "string",
  "name": "string",
  "patronymic": "string",
  "phone": "string",
  "status": "WORKING|ON_LEAVE|NOT_WORKING"
}
```

### TrainerDetailDto
```json
{
  "id": "UUID",
  "surname": "string",
  "name": "string",
  "patronymic": "string",
  "phone": "string",
  "status": "WORKING|ON_LEAVE|NOT_WORKING",
  "clientNames": ["string"]
}
```

### LockerDto
```json
{
  "id": "UUID",
  "number": "integer",
  "clientFullName": "string"
}
```

### ServiceDto
```json
{
  "id": "string",
  "name": "string",
  "clientNames": ["string"]
}
```

## Ответы об ошибках

API возвращает стандартные HTTP коды состояния:

- `200 OK` - Запрос выполнен успешно
- `201 Created` - Ресурс создан успешно
- `400 Bad Request` - Некорректные входные данные
- `404 Not Found` - Ресурс не найден
- `500 Internal Server Error` - Ошибка сервера

## Начало работы

1. **Запуск приложения**:
   ```bash
   mvn spring-boot:run
   ```

2. **Доступ к Swagger UI**:
   - Откройте `http://localhost:8090/api/swagger-ui.html`

3. **Импорт коллекции Postman**:
   - Импортируйте `SkillFitBox_API_Collection.postman_collection.json`
   - Импортируйте `SkillFitBox_Environment.postman_environment.json`

4. **Тестирование API**:
   - Используйте Swagger UI для интерактивного тестирования
   - Используйте Postman для комплексного тестирования API

## Поддержка

По вопросам или проблемам с API обращайтесь к команде разработки или обратитесь к документации Swagger UI.
