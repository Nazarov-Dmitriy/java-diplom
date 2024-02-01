# Дипломная работа

Сервис для загрузки файлов и вывода списка уже загруженных файлов пользователя.

## Запуск
Для запуска приложения нужно склонировать репозиторий

Для запуска приложения необходимо выполнить команду `docker-compose up` в корневой папке проекта.
В docker compose 4 контейнера
1. БД postgres
2. pgadmin4 порт 5050 (данные для ввхода в docker-compose)
3. Backend порт 5500

## Описание

При инициализации проекта если БД не создана , создается бд users, files, role и создается 3 тестовые пользователя

- user1 пароль123456
- user2 пароль123456
- user3 пароль123456

При авторизцации выдается jwt token на 10 часов. Данные пароля в бд зашифрованы bcrypt
Все логи работы сервера записыватся в file.log
