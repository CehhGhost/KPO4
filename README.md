# KPO4
Работа написана на основе Java 20 и Spring 3.1.0. база данных PostgreSQL. Для тестирования база данных чиститься после кажого запуска.
## Тестировние
Для тестирование используется Postman, его коллекция предоставлена в проекте (с уже предоставленными телами).
### POST http://localhost:8080/api/v1/auth/register
Отвечает за регистрацию, необходимо передать в body raw JSON формата:
* Имя
* Фамилия
* Почта
* Пароль

Также присутсвует проверка если такая почта уже существует (вернеться ошибка если это так)
Пример можно увидеть в коллекции:
```json
{
"firstname": "alibou",
"lastname": "alibou",
"email": "ghost5016628@mail.ru",
"password": "555"
}
```
После успешной авторизации пользователь будет занесен в базу данных
### POST http://localhost:8080/api/v1/auth/authenticate
Отвечает за авторизацию, необходимо передать в body JSON формата:
* Почта
* Пароль

Если пароль верный, то вернеться токен пользователя, иначе выдасться ошибка
Пример можно видеть в коллекции:
<br>Некорректный пароль(Если воспользоваться регистрацией из примера):
```json
{
    "email": "ghost5016628@mail.ru",
    "password": "5555"
}
```
<br>Корректный пароль(Если воспользоваться регистрацией из примера):
```json
{
    "email": "ghost5016628@mail.ru",
    "password": "555"
}
```
### GET http://localhost:8080/api/v1/demo-controller
Данный запрос нужет только для проверки работы системы аутентификации. Чтобы запрос сработал, необходимо в разделе аутентификации указать чистый токен (его надо взять из результата корректной авторизации). Если токен правильный, то вернеться сообщение: "Hello from secured endpoint".
В противном случае выбросится ошибка.
### GET http://localhost:8080/api/v1/user/get
Отвечает за получение пользователя по его токену, необходимо передать в body JSON формата:
* Токен

Пример можно увидеть в коллекции:
```json
{
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJnaG9zdDUwMTY2MjhAbWFpbC5ydSIsImlhdCI6MTY4Njg1NjQ2NCwiZXhwIjoxNjg2ODU3OTA0fQ.RImMfwTX0s8WaA5WIk390Yjf7GcGBWYhymws12puJjs"
}
```
<br> Помимо тела необходима авторизация, так же как и в demo-controller (изначально доступ к этой функции был только у роли ADMIN, но чтобы вам было проще тестировать был предоставлен доступ и обычным USER)
Если токены валидны и в теле и в авторизации, то выведется информация о пользователе (пароль будет в зашифрованном состоянии). Иначе выведется ошибка.