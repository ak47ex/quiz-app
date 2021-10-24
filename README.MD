# quiz-app

## Подготовка окружения
Установить docker, java 11, maven 3.6.3

## Сборка и запуск
### Сборка для развертывания в целевых средах:
`mvn clean package`

### Сборка и запуск для развертывания локально
1. `mvn clean package`
2. `docker-compose build --no-cache`
3. `docker-compose up`

Проверить, запущено ли приложение можно по адресу:

`http://localhost:8080/api/actuator/health`

Остановка приложения:
`docker-compose down`