# TCDBServer: Simple user database management system

Простейшая система управления базой данных пользователей MySQL.

Используемые технологии:
- Java 8
- MySQL Connector/J 6.0.5
- Hibernate 5.2.5
- Servlet API 3.1
- JSP / JSTL 1.2

## Описание основных возможностей

- Регистрация. Зарегистрироваться можно только с правами пользователя. Создать профиль администратора можно из панели управления администратора либо напрямую добавить пользователя с полем `role = admin` SQL-запросом
- Авторизация. После регистрация можно войти в систему под своим логином и паролем

## Запуск приложения

0. (Не обязательно) Выбрать тип используемой технологии доступа к базе данных - JDBC или Hibernate (по-умолчанию используется Hibernate)
	* Hibernate: приступить к пункту 2
  * JDBC: в конфигурационном файле `TCDBServer\src\main\db.properties` изменить 
	параметр `DAOFactoryImplementationClassName` на `database.dao.factory.JdbcDaoFactory`
0. Указать путь к базе пользователей, имя и пароль в параметре `ConnectionURL` файла конфигурации `TCDBServer\src\main\db.properties` 
(при использовании JDBC) или параметрах `hibernate.connection.url`, `hibernate.connection.username`, `hibernate.connection.password` 
файла `TCDBServer\src\main\hibernate.cfg.xml` (при использовании hibernate)
0. Выполнить цель `mvn install`. В итоге будет собран war архив.
0. Развернуть архив на сервере. Я использовал Tomcat 8.5.

### Подробнее о запуске приложения на сервере Tomcat 8.5 из IntelliJIDEA

- Устанавливаем Tomcat с официального сайта, распаковываем, прописываем необходимые переменные среды, указываем путь к Tomcat в 
IntelliJIDEA
- Заходим в раздел Run/Debug Configuration > Add New Configuration > Tomcat Server > Local: ![Создание Tomcat конфигурации запуска](http://i.piccy.info/i9/261462933d520ef793032b07b9abe0a6/1487953491/49821/1122373/2017_02_24_19_24_08_800.jpg)
- Переходим в раздел Deployment > Add > Artifact > tcdbserver:war exploded > OK > OK
![Artifact deployment](http://i.piccy.info/i9/f1bab4e3e085c9427ea0d7db17add13c/1487954240/42741/1122373/2_800.jpg)
- Запускаем как обычный проект `Shift + F10`
- Переходим в браузере по адресу [http://localhost:8080/](http://localhost:8080/)
