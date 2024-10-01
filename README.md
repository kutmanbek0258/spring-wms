## Структура репозитория

1. [j-sso](./j-sso/README.md) - Single Sign On сервис.
2. [s-wms](./s-wms/README.md) - Warehouse Management Service, который работает в паре с `j-sso`
3. [j-swagger-ui](./j-swagger-ui/README.md) - документация сервисов в swagger-ui.
4. [s-wms-client](./s-wms-client/README.md) - клиент WMS аутентифицируется с `j-sso`.

## Версии и используемые инструменты

Для работы приложения и ведения разработки вам потребуется:

1. Java 17
2. Node 16
3. Maven 3

Остальные версии используемых библиотек смотрите в [pom.xml](pom.xml).

## Сборка всех сервисов

Для сборки всех сервисов выполните команду:

```shell
clean install -DskipTests -P dev,client-build-and-copy
```

## Запуск в среде разработки

В каждом сервисе, в файле README описано как собрать и запустить приложение. Также для запуска доступны готовые
конфигурации для IntelliJ IDEA. Они находятся тут [runConfigurations](.idea/runConfigurations).

Также для запуска доступна подготовленная конфигурация docker compose ([docker-compose.yml](docker-compose.yml)).

Чтобы запустить сразу все сервисы и наслаждаться изучением их работы используйте подготовленные конфигурации IDEA:

- Шаг 0. Указать корректные значения environment variables для j-sso. (Ищи в
  файле [docker-compose.yml](docker-compose.yml) выражение `<set_value>`)
- Шаг 1. Запустить БД. Используйте конфигурацию [run_database.xml](.idea/runConfigurations/run_database.xml).
- Шаг 2. Накатить схему БД для j-sso. Перейдите в модуль j-sso и выполните следующую команду:
  ```shell
     mvn liquibase:update -Dliquibase.searchPath=./
   ```
- Шаг 3. Собрать и запустить все сервисы. Используйте конфигурацию
  запуска [run_all_services.xml](.idea/runConfigurations/run_all_services.xml). Она сделает сборку всего приложения и
  запустит все сервисы.
  
Проект является развитием разработки Данила Иванова в статье [Строим свой SSO сервер используя Spring Authorization Server](https://habr.com/ru/articles/737548/)

[тут оригинал кода](https://github.com/dlabs71/spring-authorization-server-example/tree/latest)