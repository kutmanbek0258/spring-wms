# WMS-service. OAuth2 Resource Server.

Данное приложение предназначено для демонстрации взаимодействия OAuth2 Resource Server, настроенного с
использованием `spring-boot-starter-oauth2-resource-server`, с сервером авторизации `j-sso`.

## Сборка и запуск

WMS-service представляет собой систему управления складом.

Сборка производиться следующей командой

```shell
mvn clean install -DskipTests
```

Для запуска можете использовать собранный jar архив или использовать подготовленную конфигурацию запуска для IntelliJ
IDEA [WMSApplication.xml](../.idea/runConfigurations/WMSApplication.xml).

## Environment variables для запуска приложения

| Наименование          | Значение по умолчанию | Описание                  |
|-----------------------|-----------------------|---------------------------|
| JSERVICE_ANSI_ENABLED | ALWAYS                | Включение подсветки логов |
| JSERVICE_SSO_URL      | http://localhost:7777 | URL подключения к j-sso   |   