# SkillFitBox: Система управления фитнес-центром

## Описание

SkillFitBox - это Spring Boot приложение, демонстрирующее различные типы связей в базе данных:
- **Один-к-одному** (Client - Locker)
- **Один-ко-многим** (Trainer - Client)  
- **Многие-ко-многим** (Client - Service)

Приложение представляет собой систему управления фитнес-центром с возможностью управления клиентами, тренерами, шкафчиками и дополнительными услугами.

## Технологический стек

- **Spring Boot 3.2.0**
- **Java 17**
- **PostgreSQL** (без индексов)
- **Spring JDBC Template** с HikariCP connection pool
- **Flyway** для миграций базы данных
- **Maven** для сборки
- **MapStruct** для маппинга объектов
- **Lombok** для сокращения boilerplate кода
- **SLF4J + Logback** для логирования
- **Jakarta Validation** для валидации входных данных
- **Spring MVC** с FreeMarker для веб-интерфейса

## Архитектура

Приложение построено по многослойной архитектуре:

```
├── entity/          # Сущности базы данных
├── dto/             # Data Transfer Objects
├── mapper/          # MapStruct мапперы
├── repository/      # Интерфейсы репозиториев
│   ├── impl/        # Spring JDBC Template реализации
│   └── legacy/      # Legacy JDBC реализации
├── service/         # Бизнес-логика
├── controller/      # REST контроллеры
├── ui/              # Веб-интерфейс контроллеры
└── config/          # Конфигурация приложения
```

## Сущности

### Client (Клиент)
- `id` (UUID) - уникальный идентификатор
- `surname` (String) - фамилия
- `name` (String) - имя
- `patronymic` (String) - отчество (опционально)
- `birthday` (Date) - дата рождения
- `phone` (String) - номер телефона
- `email` (String) - email адрес
- `is_active` (Boolean) - активен ли клиент
- `locker_id` (UUID) - связь с шкафчиком (один-к-одному)
- `trainer_id` (UUID) - связь с тренером (один-ко-многим)

### Trainer (Тренер)
- `id` (UUID) - уникальный идентификатор
- `surname` (String) - фамилия
- `name` (String) - имя
- `patronymic` (String) - отчество (опционально)
- `phone` (String) - номер телефона
- `status` (Enum) - статус тренера (WORKING, ON_LEAVE, NOT_WORKING)

### Locker (Шкафчик)
- `id` (UUID) - уникальный идентификатор
- `number` (Integer) - номер шкафчика
- `client_id` (UUID) - связь с клиентом (один-к-одному)

### Service (Дополнительная услуга)
- `id` (String) - уникальный идентификатор (латинские символы)
- `name` (String) - название услуги (на русском)

### Client Services (Связь клиент-услуги)
- `client_id` (UUID) - идентификатор клиента
- `service_id` (String) - идентификатор услуги

## Начальные данные

При запуске приложения автоматически создаются:
- 20 шкафчиков с `client_id = null`
- 5 дополнительных услуг: "Солярий", "Бассейн", "Сауна", "Криосауна", "Кроссфит"

## Требования

- Java 17 или выше
- Maven 3.6 или выше
- PostgreSQL 12 или выше

## Установка и запуск

### 1. Клонирование репозитория

```bash
git clone <repository-url>
cd skillfitbox
```

### 2. Настройка базы данных

Создайте базу данных PostgreSQL:

```sql
CREATE DATABASE skillfitbox;
```

### 3. Настройка подключения к базе данных

Настройте подключение к базе данных в файле `src/main/resources/application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5434/postgres
    username: postgres
    password: your_password
    driver-class-name: org.postgresql.Driver
```

### 4. Сборка и запуск

```bash
# Сборка проекта
mvn clean compile

# Запуск приложения
mvn spring-boot:run
```

Или используйте JAR файл:

```bash
# Сборка JAR
mvn clean package

# Запуск JAR
java -jar target/skillfitbox-1.0.0.jar
```

### 5. Проверка работы

Приложение будет доступно по адресу: `http://localhost:8090/api`

UI приложения доступен из браузера по адресу `http://localhost:8090/`

Swagger UI документация доступна по адресу: `http://localhost:8090/api/swagger-ui.html`

## API Endpoints

### Клиенты

- `POST /api/clients` - добавить клиента
- `PUT /api/clients/{id}` - обновить информацию о клиенте
- `GET /api/clients` - получить всех клиентов
- `GET /api/clients/{id}` - получить краткую информацию о клиенте
- `GET /api/clients/{id}/detail` - получить подробную информацию о клиенте
- `PATCH /api/clients/{id}/status` - активировать/деактивировать клиента
- `POST /api/clients/{clientId}/trainer/{trainerId}` - назначить тренера клиенту
- `POST /api/clients/{clientId}/additionalServices/{serviceId}` - добавить услугу клиенту
- `POST /api/clients/{clientId}/locker/{lockerId}` - назначить шкафчик клиенту

### Тренеры

- `POST /api/trainers` - добавить тренера
- `PUT /api/trainers/{id}` - обновить информацию о тренере
- `PATCH /api/trainers/{id}/status` - изменить статус тренера
- `GET /api/trainers/{id}/detail` - получить подробную информацию о тренере
- `GET /api/trainers` - получить список тренеров

### Шкафчики

- `GET /api/lockers` - получить информацию о всех шкафчиках

### Услуги

- `GET /api/additionalServices` - получить список всех услуг
- `GET /api/additionalServices/{id}` - получить информацию об услуге с клиентами

## Примеры запросов

### Добавление клиента

```bash
curl -X POST http://localhost:8080/api/clients \
  -H "Content-Type: application/json" \
  -d '{
    "surname": "Иванов",
    "name": "Иван",
    "patronymic": "Иванович",
    "birthday": "1990-01-15",
    "phone": "+7-900-123-45-67",
    "email": "ivanov@example.com"
  }'
```

### Добавление тренера

```bash
curl -X POST http://localhost:8080/api/trainers \
  -H "Content-Type: application/json" \
  -d '{
    "surname": "Петров",
    "name": "Петр",
    "patronymic": "Петрович",
    "phone": "+7-900-987-65-43",
    "status": "WORKING"
  }'
```

### Получение подробной информации о клиенте

```bash
curl http://localhost:8080/api/clients/{client-id}/detail
```

## Особенности реализации

1. **Spring JDBC Template** - используется Spring JDBC Template с HikariCP connection pool для упрощения работы с базой данных
2. **Repository Pattern** - интерфейсы репозиториев с реализациями в пакете `impl/`
3. **Transaction Management** - декларативное управление транзакциями с помощью `@Transactional`
4. **Comprehensive Logging** - подробное логирование всех операций с базой данных
5. **MapStruct** - автоматическая генерация мапперов между сущностями и DTO
6. **Lombok** - автоматическая генерация геттеров, сеттеров, конструкторов
7. **Flyway** - автоматическое выполнение миграций при запуске
8. **Валидация** - Jakarta Validation для проверки входных данных
9. **JOIN запросы** - оптимизированные запросы с использованием LEFT JOIN
10. **Web UI** - полнофункциональный веб-интерфейс на Spring MVC + FreeMarker

## Миграция на Spring JDBC Template

Проект был мигрирован с нативного JDBC на Spring JDBC Template для улучшения:

### Преимущества миграции:

- **Упрощение кода** - Spring JDBC Template автоматически управляет ресурсами (Connection, PreparedStatement, ResultSet)
- **Автоматическая обработка исключений** - Spring автоматически преобразует SQLException в более понятные исключения
- **Управление транзакциями** - декларативное управление транзакциями через `@Transactional`
- **Лучшая производительность** - оптимизированная работа с пулом соединений
- **Comprehensive Logging** - подробное логирование всех операций для отладки

### Структура репозиториев:

```
repository/
├── ClientRepository.java                    # Интерфейс
├── TrainerRepository.java                   # Интерфейс
├── LockerRepository.java                    # Интерфейс
├── AdditionalServiceRepository.java         # Интерфейс
├── impl/                                    # Spring JDBC Template реализации
│   ├── JdbcTemplateClientRepository.java
│   ├── JdbcTemplateTrainerRepository.java
│   ├── JdbcTemplateLockerRepository.java
│   └── JdbcTemplateAdditionalServiceRepository.java
└── legacy/                                  # Legacy JDBC реализации (сохранены для справки)
    ├── LegacyClientRepository.java
    ├── LegacyTrainerRepository.java
    ├── LegacyLockerRepository.java
    └── LegacyAdditionalServiceRepository.java
```

### Ключевые изменения:

1. **Интерфейсы репозиториев** - выделены в отдельные интерфейсы для лучшей архитектуры
2. **Spring JDBC Template** - заменил нативный JDBC код
3. **Row Mappers** - функциональные интерфейсы для маппинга ResultSet в объекты
4. **Transaction Management** - добавлены `@Transactional` аннотации в сервисном слое
5. **Logging** - добавлено подробное логирование всех операций
6. **Configuration** - база данных настроена через `application.yml`
