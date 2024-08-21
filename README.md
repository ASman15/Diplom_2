# Диплом часть 2
## Протестировано API сайта [Stellar Burgers](https://stellarburgers.nomoreparties.site/)
### Выполнены проверки создания, авторизации и удаления нового пользователя, создание нового заказа, а также получение списка заказов авторизованным и не авторизованным пользователем.
### Были использованы JUnit v.4.3.12, Maven v.3.8.7. Подключены Selenium v.3.141.59, Rest Assured v.5.4.0. Для формирования отчёта подключен модуль Allure v.2.27.0.
### Для запуска тестов используется команда `mvn clean test`.
### Для формирования отчёта использовалась команда `allure serve target/surefire-reports/`.