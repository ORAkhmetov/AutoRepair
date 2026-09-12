# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project

AutoRepair — учебное/пет-проектное Spring Boot MVC-приложение для учёта автосервиса (клиенты, автомобили, обращения, заказ-наряды), с серверным рендерингом Thymeleaf и аутентификацией через Spring Security.

## Commands

Проект собирается через Maven Wrapper.

```
./mvnw clean install       # сборка + тесты
./mvnw spring-boot:run     # запуск локально (порт 8080)
./mvnw test                # запуск всех тестов
./mvnw test -Dtest=ClassName#methodName   # запуск одного теста
```

Windows: используйте `mvnw.cmd` вместо `./mvnw`.

Локально нужен PostgreSQL (см. `docker-compose.yaml`, база `auto_db`, схема `core`). Миграции Flyway лежат в `src/main/resources/db/migration` и применяются автоматически при старте (`baseline-on-migrate: true`).

Единственный тестовый класс (`AutoRepairApplicationTests`) сейчас помечен `@Disabled` — реальных тестов в проекте нет.

Docker-сборка: `Dockerfile` ожидает готовый `target/auto_repair.jar` (собрать через `mvnw clean install` перед `docker build`). CI (`.github/workflows/maven.yml`) на пуш/PR в `master` собирает jar, пушит образ в Docker Hub и деплоит по SSH на VDS через `docker compose`.

## Architecture

Пакеты организованы по доменным сущностям (feature-package), а не по слоям: `client/`, `car/`, `appeal/`, `order/`, `security/`. Внутри каждого пакета — одинаковый набор классов:

- `<Entity>.java` — JPA-сущность (Lombok `@Getter/@Setter`).
- `<Entity>DTO.java` — DTO для форм/Thymeleaf.
- `<Entity>Mapper.java` — ручной маппинг Entity <-> DTO (через ModelMapper).
- `<Entity>Validator.java` — Spring `Validator`, вызывается вручную в контроллере перед сохранением.
- `<Entities>Repository.java` — Spring Data JPA репозиторий, часто с производными методами поиска (`findByXContainingIgnoreCase` и т.п.).
- `<Entities>Service` + `<Entities>ServiceImpl` — бизнес-логика между контроллером и репозиторием.
- `<Entities>Controller.java` — Spring MVC контроллер (`@Controller`, не REST), возвращает имена Thymeleaf-шаблонов, использует hidden method filter для PATCH/DELETE через HTML-формы.

Доменная модель (по FK): `Client` 1—N `Car` (владелец) → `Car` 1—N `Appeal` (обращение по машине) → `Appeal` 1—N `Order` (заказ-наряд) и 1—N `PhotoAppeal`. Таблицы в БД называются в единственном числе (`client`, `car`, `appeal`, `aorder` — не `order`, т.к. это зарезервированное слово SQL).

Шаблоны Thymeleaf лежат в `src/main/resources/templates/<entity>/` (index/new/edit/show), общий layout — `templates/common.html`. Раздача форм и обработка идут через один и тот же контроллер (классический MVC CRUD, без REST API), кроме `ProbaController` (`/proba/str`) — тестовый REST-эндпоинт.

`security/` содержит отдельный `config/SecurityConfig.java` (form login, роли `USER`/`ADMIN`, BCrypt) и сервисы регистрации/аутентификации (`AUserDetailsService`, `RegistrationService`, `AdminService`). Публичные без авторизации пути: `/auth/login`, `/auth/registration`, `/error`, `/proba/**`.

## Git Workflow

- Ветки создаются от `master` по шаблону `feature/DEV-x` или `bugfix/DEV-x`, где `x` — номер задачи. Если номер задачи неизвестен из контекста — уточнить его у оператора, не придумывать самостоятельно.
- Сообщение коммита формируется по шаблону `DEV-x (feature|bugfix) Описание правок`, где `feature`/`bugfix` совпадает с типом ветки, а описание — краткая суть изменения на русском языке.

  Пример: `DEV-142 (feature): Добавлена валидация email при регистрации клиента`

## Notes

- `src/main/resources/application.yaml` и `docker-compose.yaml` содержат захардкоженные учётные данные локальной dev-БД PostgreSQL, закоммиченные в репозиторий. Это не production-секреты, но при доработке конфигурации стоит вынести их в переменные окружения, а не добавлять новые захардкоженные креды.
- Загрузка фото (`PhotoAppeal`) хранит файлы на диске по пути из `application.upload.path` (сейчас Windows-путь `C:/Data/img/`).
