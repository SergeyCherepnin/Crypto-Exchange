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
* *FlywayDB*
* *PostgreSQL*
* *Slf4g*

## Запуск приложения
* Необходимо клонировать данный репозиторий
* В aplication.properties ввести свои данные username, password
* В PosrgeSQL создать базу данных "crypto_exchange" `CREATE DATABASE crypto_exchange;`
* Запустить приложение 

________________________________________________________________________________________________________________________
## Решение

В приложении была реализована аутентификация и авторизация пользователей с помощью *JWT* токена.
В качестве хранилища была использована *PostgreSQL*. Добавлена миграция БД с помощью *Flywaydb*.
Имя и пароль администратора: "username": "admin", "password": "admin"

В headers всех запросов(кроме login и register) нужно ввести key: Authorization,  value: Bearer_'полученный токен'

Время действия токена выставлено на 1 час, после чего нужно снова войти в систему

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
    "username": "admin",
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
    "currency": "RUB",
    "count": 1000
}`
![Withdraw](https://github.com/SergeyCherepnin/Crypto-Exchange/blob/master/screenshots/withdraw.png)

Response: `{
    "BTC": 100,
    "TON": 200,
    "RUB": 9000
}`
__________________________________________________________________________________________________________________________________________________
***Актуальный курс валют***(доступен и пользователю и администратору)

GET-запрос `localhost:8080/rates`

RequestBody: `{
    "currency": "TON"
}`
![Rates](https://github.com/SergeyCherepnin/Crypto-Exchange/blob/master/screenshots/rates.png)

Response: `{
    "BTC": 0.1,
    "RUB": 10.0
}`
__________________________________________________________________________________________________________________________________________________
***Обмен валюты по установленному курсу***

POST-запрос `localhost:8080/user/wallet/exchange`

RequestBody: `{
    "currency_from": "BTC",
    "currency_to": "TON",
    "amount" : 10
}`
![Exchange](https://github.com/SergeyCherepnin/Crypto-Exchange/blob/master/screenshots/exchange.png)

Response: `{
    "amount_from": "10.0",
    "currency_to": "TON",
    "currency_from": "BTC",
    "amount_to": "100.0"
}`
__________________________________________________________________________________________________________________________________________________
### API для администратора
***Изменение курса валют***

POST-запрос `localhost:8080/admin/rates/update`

RequestBody: `{
    "base_currency": "RUB",
    "btc": 0.02,
    "ton": 0.5
}`
![Update_rates](https://github.com/SergeyCherepnin/Crypto-Exchange/blob/master/screenshots/update_rates.png)

Response: `{
    "BTC": 0.02,
    "TON": 0.5
}`
__________________________________________________________________________________________________________________________________________________
***Общая сумма на всех пользовательских счетах для указанной валюты.***

GET-запрос `localhost:8080/admin/amount`

RequestBody: `{
    "currency": "RUB"
}`
![Amount](https://github.com/SergeyCherepnin/Crypto-Exchange/blob/master/screenshots/amount.png)

Response: `{
    "RUB": 108699.0
}`
__________________________________________________________________________________________________________________________________________________
***Количество операций, которые были проведены за указанный период***

GET-запрос `localhost:8080/admin/transactions`

RequestBody: `{
    "date_from": "18.02.2023",
    "date_to": "02.03.2023"
}`
![Transaction_count](https://github.com/SergeyCherepnin/Crypto-Exchange/blob/master/screenshots/transact_count.png)

Response: `{
    "transaction_count": 17
}`
__________________________________________________________________________________________________________________________________________________
***403 Forbidden***
При попытке пользователя выполнить методы админа:
![Transaction_count](https://github.com/SergeyCherepnin/Crypto-Exchange/blob/master/screenshots/forbidden_403.png)

