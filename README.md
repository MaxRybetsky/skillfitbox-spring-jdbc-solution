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
- **JDBC** с кастомным DataSource bean и HikariCP
- **Flyway** для миграций базы данных
- **Maven** для сборки
- **MapStruct** для маппинга объектов
- **Lombok** для сокращения boilerplate кода
- **SLF4J + Logback** для логирования
- **Jakarta Validation** для валидации входных данных

## Архитектура

Приложение построено по многослойной архитектуре:

```
├── entity/          # Сущности базы данных
├── dto/             # Data Transfer Objects
├── mapper/          # MapStruct мапперы
├── repository/      # Слой доступа к данным (JDBC)
├── additionalService/         # Бизнес-логика
├── controller/      # REST контроллеры
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

### 3. Настройка переменных окружения

Создайте файл `.env` или установите переменные окружения:

```bash
export DB_USERNAME=postgres
export DB_PASSWORD=your_password
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

1. **JDBC с HikariCP** - используется нативный JDBC с пулом соединений HikariCP
2. **MapStruct** - автоматическая генерация мапперов между сущностями и DTO
3. **Lombok** - автоматическая генерация геттеров, сеттеров, конструкторов
4. **Flyway** - автоматическое выполнение миграций при запуске
5. **Валидация** - Jakarta Validation для проверки входных данных
6. **Транзакции** - Spring транзакции для обеспечения целостности данных
7. **JOIN запросы** - оптимизированные запросы с использованием LEFT JOIN
