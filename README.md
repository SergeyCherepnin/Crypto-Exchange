# Crypto-Exchange
## Задание
Приложение должно реализовывать упрощенный функционал криптобиржи.
В системе предполагается две роли: пользователь и администратор. 

Пользователь должен иметь возможность зарегистрироваться, пополнить кошелёк, вывести
деньги, купить/продать крипту.
Администратор должен иметь возможность изменить курс крипты, посмотреть статистику
по всем кошелькам пользователей, статистику по торгам за определенный промежуток
времени.

Приложение должно иметь REST API (принимать REST запросы и возвращать ответы)

_______________________________________________________________________________________________________________________
## Использованные технологии:
* *Java 1.8*
* *Spring Boot*
* *Spring Data JPA(Hibernate)*
* *Spring Security(jwt)*
* *Maven*
* *Lombok*
* *Flyway*
* *PostgreSQL*
* *Slf4g*

## Запуск приложения
* Необходимо клонировать данный репозиторий
* В aplication.properties ввести свои данные username, password
* В PosrgeSQL создать базу данных "crypto_exchange" `CREATE DATABASE crypto_exchange;`
* Запустить приложение 

________________________________________________________________________________________________________________________
## Решение

В headers всех запросов(кроме login и register) нужно ввести key: Authorization,  value: Bearer_'полученный токен'

***Регистрация нового пользователя***

POST-запрос. `localhost:8080/auth/register`

RequestBody: `{
    "username":"sergei",
    "email":"cherepnin@gmail.com",
    "password":"12345"
}`
![Register](https://github.com/SergeyCherepnin/Crypto-Exchange/blob/master/screenshots/%D1%80%D0%B5%D0%B3%D0%B8%D1%81%D1%82%D1%80%D0%B0%D1%86%D0%B8%D1%8F.png)
В ответ получаем jwt токен

Rsponse: `{
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzZXJnZWkiLCJpYXQiOjE2Nzc2ODk4OTcsImV4cCI6MTY3NzY5MzQ5N30.gvTH2A1BkpoQpfU-7Ff6udPe3hbW76e65OdwQJe5MVA"
}`
_________________________________________________________________________________________________________________________________________________
***Вход в систему по логину и паролю***(в ответ также генерируется токен)

POST-запрос `localhost:8080/auth/login`

RequestBody: `{
    "username":"admin",
    "password":"admin"
}`
![LogIn](https://github.com/SergeyCherepnin/Crypto-Exchange/blob/master/screenshots/login.png)

Response: `{
    "username": "sergei",
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzZXJnZWkiLCJpYXQiOjE2Nzc2OTM4MzMsImV4cCI6MTY3NzY5NzQzM30.GVW0FMCSbrvAnXaC3G0IQbwTZBmr4RmQkqv34S04Hv0"
}`
__________________________________________________________________________________________________________________________________________________
***Просмотр баланса своего кошелька***

GET-запрос `localhost:8080/user/wallet/balance`
![Balance](https://github.com/SergeyCherepnin/Crypto-Exchange/blob/master/screenshots/balance.png)

Response: `{
    "BTC": 0.0,
    "TON": 0.0,
    "RUB": 0.0
}`
__________________________________________________________________________________________________________________________________________________
***Пополнение кошелька***

POST-запрос `localhost:8080/user/wallet/deposit`

RequestBody: `{
    "currency": "TON",
    "count": 100
}`
![Deposit](https://github.com/SergeyCherepnin/Crypto-Exchange/blob/master/screenshots/deposit.png)

Response: `{
    "BTC": 0,
    "TON": 100,
    "RUB": 0
}`
__________________________________________________________________________________________________________________________________________________
***Вывод денег со счета***

POST-запрос `localhost:8080/user/wallet/withdraw`

RequestBody: `{
    "currency": "TON",
    "count": 100
}`
![Withdraw](https://github.com/SergeyCherepnin/Crypto-Exchange/blob/master/screenshots/withdraw.png)

Response: `{
    "BTC": 0,
    "TON": 100,
    "RUB": 0
}`


