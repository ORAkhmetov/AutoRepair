# Тестирование

## Назначение

Тестирование решает задачи:

1. фиксации состояния объектов системы
2. диагностирования того или иного состояния контекста в изолированных условиях
3. получение дополнительного представления о реализации наряду, дополняя документацию use-кейсами

### Применяемое тестирование в проекте:

1. юнит тестирование
2. интеграционное тестирование
    1. интеграционное тестирование с применением полного контекста
    2. интеграционное тестирование с применением частично замещенного моками и заглушками контекста
    3. интеграционное тестирование с применением контекста, дополненного тестовыми контейнерами

### Технологии

- Для проверки условий используется библиотека `assertj`, принятая в качестве внутреннего стандарта.
- Для создания облегченного изолированного контекста проверки из моков используется `Mockito`. Не использовать lenient в @BeforeEach секции, предпочитать мокирование в каждом отдельном тесте.
- Для создания отладочного интеграционного окружения используется `spring-boot-starter-test` и производные от него.
- Для дополнения интеграционного контекста применяются `testcontainers`.

> **Открытый вопрос:** `assertj` и `Mockito` уже доступны транзитивно через `spring-boot-starter-test`, но зависимости `testcontainers`
> (`testcontainers-junit-jupiter`, `testcontainers-postgresql`) в `pom.xml` проекта пока нет. Пока модуль не подключён, пункт
> "интеграционное тестирование с применением контекста, дополненного тестовыми контейнерами" не может быть реализован — при появлении
> первых тестов такого рода нужно будет добавить зависимости и согласовать версию testcontainers-bom с `spring-boot-starter-parent`
> (сейчас 3.0.1).

### Тестирование по слоям проекта

Проект организован по feature-package (`client/`, `car/`, `appeal/`, `order/`, `security/`), внутри каждого пакета — фиксированный набор
классов (см. `CLAUDE.md`). Ниже — соответствие слоя и типа теста из этого документа:

| слой                                   | тип теста                                    | примечание                                                                                     |
|-----------------------------------------|-----------------------------------------------|--------------------------------------------------------------------------------------------------|
| `<Entity>Validator`                     | юнит                                          | чистая логика валидации, без контекста Spring                                                    |
| `<Entity>Mapper`                        | юнит                                          | проверка маппинга Entity <-> DTO через `ModelMapper`                                              |
| `<Entities>ServiceImpl`                  | юнит с моками репозитория/маппера             | `Mockito`, `@ExtendWith(MockitoExtension.class)`, `@InjectMocks` — см. пример 2 выше              |
| `<Entities>Repository`                  | интеграционный, `@DataJpaTest`                | производные методы поиска (`findByXContainingIgnoreCase` и т.п.) стоит проверять на реальном диалекте Postgres — кандидат на testcontainers, когда модуль будет подключён |
| `<Entities>Controller`                  | интеграционный, `@WebMvcTest` + `MockMvc`     | `spring-security-test` уже подключён — использовать для проверки form login, ролей `USER`/`ADMIN` и hidden method filter (PATCH/DELETE через HTML-формы) |
| полный контекст (Security + Flyway + БД) | интеграционный, `@SpringBootTest`             | на данный момент единственный класс такого рода — `AutoRepairApplicationTests`, помечен `@Disabled` |

### Порядок тестов

#### Написание и исполнение

В проекте активно применяется парадигма TDD, из которой вытекает частая наобходимость учитывать порядок выполнения тестов. Сами тесты в
таком случае должны строиться от более общих тест-кейсов к более частным. Таким образом, появляется возможность определить масштаб
несоответствий предварительно зафиксированного поведения к фактическому состоянию.

| группа тестов | описание                                                                           |
|---------------|------------------------------------------------------------------------------------|
| обобщенная    | проверяется контекст, свойства контекста, условия БД и тп                          |
| частная       | проверяется работа конкретного компонента, сервиса или объекта, частной реализации |

#### Применение упорядочивания тестов

Использование `@Order` позволяет выстроить нужную последовательность в рамках классов тестов, вложенных классов (`@Nested`) и методов
тестирования. Поддержка упорядочивания в общем случае требует наличия аннотирования класса:

```java

@Order(1)
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SubjectFirstGroupTest {

    @Test
    @Order(1)
    void testMustBeFirstTopLevelGroup1() {
        // ...
    }

    @Test
    @Order(2)
    void testMustBeSecondTopLevelGroup1() {
        // ...
    }

    @Nested
    @Order(3)
    class NestedSubjectTests {

        @Test
        @Order(1)
        void testMustBeFirstNestedLevelGroup1() {
            // ...
        }

        @Test
        @Order(2)
        void testMustBeSecondNestedLevelGroup1() {
            // ...
        }

    }

}

@Order(2)
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SubjectSecondGroupTest {

    @Test
    @Order(1)
    void testMustBeFirstTopLevelGroup2() {
        // ...
    }

}
```

В данном случае выполнение тестов будет происходить в следующем порядке:

```
testMustBeFirstTopLevelGroup1      // 1
testMustBeSecondTopLevelGroup1     // 2 
testMustBeFirstNestedLevelGroup1   // 3
testMustBeSecondNestedLevelGroup1  // 4
testMustBeFirstTopLevelGroup2      // 5
```

### Структура типового теста:

Структурно тест должен содержать раздельные секции:

| наименование | пример    | обязательность       | назначение                                            |
|--------------|-----------|----------------------|-------------------------------------------------------|
| given        | // given  | -                    | определение начального предусловия                    |
| and          | // and    | -                    | дополнение предусловия или пост-проверки              |
| when         | // when   | -                    | ключевое действие над проверяемым объектом            |
| then         | // then   | + если вместо expect | определение проверок                                  |
| expect       | // expect | + если вместо then   | определение проверок (как правило, однострочный тест) |

Выделение секций внутри теста позволяет отделить настройку теста от ключевого действия, а также формализовать проверку. Для случаев
комплексных проверок предлагается воспользоваться подходом [упорядочивания тестов](#порядок-тестов).

```java

@Test
@DisplayName("...")
void testMultipleLinesStandard() {
    // given
    // основной блок предусловия

    // and
    // дополнительное определение предусловия

    // when
    // единственное тестируемое действие 

    // then
    // проверка результата | проверка общего состояния контекста

    // and
    // (опционально) продожение проверки результата 
}

```

либо:

```java

@Test
@DisplayName("...")
void testShortStandard() {
    // when
    // единственное тестируемое действие 

    // expect (для однострочных тестов или для случая схождения в тесте given + when)
    // проверка результата | проверка общего состояния контекста
}

```

Пример 1:

```java

@Test
@DisplayName("Должен успешно создать объект")
void testObjectCreatedSuccess() {
    // given
    var code = "test_code";

    // and
    var entity = new EntityObject();
    entity.setCode(code);

    // when
    var result = subject.create(entity);

    // then
    assertThat(result.getId()).isNotNull();
    assertThat(result.getCode()).isEqualTo(code);
}
```

Пример 2 (использование моков):

```java

@ExtendWith(MockitoExtension.class)
class SubjectTest {

    // объявлен маппер, вызываемый реализацией сервиса
    @Mock
    SubjectMapper mapper;

    // объявлен объект проверки
    @InjectMocks
    SubjectService subject;

    @Test
    @DisplayName("Должен найти объект по идентификатору во внешнем API и вызвать маппинг")
    void testFindExistingObjectByIdInExternalApiAndCallMapper() {
        // given
        var id = UUID.randomUUID();

        // and
        var stored = subject.create(new SubjectObject().withId(id));

        // when
        var result = subject.findById(id);

        // then
        assertThat(result)
            .isPresent()
            .get()
            .extracting(SubjectObject::id)
            .isEqualTo(id);

        // and
        var captor = ArgumentCaptor.forClass(SubjectObject.class);
        verify(mapper).toDto(captor.capture());
        assertThat(captor.getValue()).isNotNull();
    }

}

```

### Тестирование сложных сценариев

Сложные сценарии тестов, подразумевающие создание комплексного созависимого состояния должны быть сведены к набору простых
последовательностей проверок из элементарных тестов, образующих последовательность в рамках `@Nested`-аннотированных классов.

Пример:

```java

@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ComplexSubjectTest {

    @Test
    @Order(1)
    @DisplayName("Верхнеуровневая проверка")
    void testShallowCheck() {
        // ...
    }

    @Nested
    @Order(2)
    class NestedSubjectTests {

        @Test
        @Order(1)
        @DisplayName("Проверка шага 1")
        void testStep1() {
            // ...
        }

        @Test
        @Order(2)
        @DisplayName("Проверка шага 2")
        void testStep2() {
            // ...
        }

    }

}

```