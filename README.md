# Курсовой проект "Сетевой чат"

## Описание проекта

В данном проекте разработаны два приложения для обмена текстовыми сообщениями по сети с помощью
консоли (терминала) между двумя и более пользователями.

**Первое приложение - сервер чата.** Ожидает подключения пользователей.

**Второе приложение - клиент чата.** Подключается к серверу чата и осуществляет доставку и 
получение новых сообщений.

## Содержание

Репозиторий содержит многомодульный проект networkchat. Который состоит из двух модулей: 
[client](client)(клиент чата) и [server](server)(сервер чата). Модульность реализована 
maven-сборщиком.

Классы [server](server):
- [Main.class](server/src/main/java/ru/netology/Main.java) - старт приложения. Инициализирует 
  Server.class;
- [Server.class](server/src/main/java/ru/netology/Server.java) - Хранит мапу [клиентов](server/src/main/java/ru/netology/Client.java). Открывает 
  ServerSocket. Ждет подключения клиента serverSocket.accept(). После подключения, создает новый 
  поток [ClientThread](server/src/main/java/ru/netology/ClientThread.java). Это происходит в 
  бесконечном цикле;
- [ClientThread](server/src/main/java/ru/netology/ClientThread.java) - Поток для обработки 
  сообщений от клиента и отправки их всем клиентам сервера. Отправка происходит за счет метода 
  sendMessageAll(Message msg) класса [Server.class](server/src/main/java/ru/netology/Server.java);
- [Client.class](server/src/main/java/ru/netology/Client.java) - Хранит id, name и clientThread с 
  которым ассоциируется данный клиент;
- [Message.class](server/src/main/java/ru/netology/Message.java) - Класс сообщение для отправки 
и приема сообщений. Конвертируется в строку и обратно с помощью gson;
- [Logger.class](server/src/main/java/ru/netology/Logger.java) - Ведет логи программы в файле 
  log_server.txt. Реализован по шаблону Singleton. Потокобезопасен за счет Enum;
- [Settings.class](server/src/main/java/ru/netology/Settings.java) - Считывает данные из 
  settings_server.properties. Там находятся данные о номере порта и т.д.


Классы [client](client):
- [Main.class](client/src/main/java/ru/netology/Main.java) - старт приложения. Инициализирует
  Client.class;
- [Client.class](client/src/main/java/ru/netology/Client.java) - Открывает Socket. 
  Запрашивает имя у пользователя. Отправляет стартовое сообщение серверу. Создает новый поток 
  для прослушивания входных сообщений от сервера
  [ListenThread](client/src/main/java/ru/netology/ListenThread.java). Сам отправляет сообщения, 
  введенные пользователем на сервер. Отправка происходит, пока пользователь не введет "/exit". 
  Тогда прерывается ListenThread interrupt().
- [ListenThread](client/src/main/java/ru/netology/ListenThread.java) - Поток для обработки 
  приходящих сообщений от сервера.
- [Message.class](client/src/main/java/ru/netology/Message.java) - Класс сообщение для отправки
  и приема сообщений. Конвертируется в строку и обратно с помощью gson;
- [Logger.class](client/src/main/java/ru/netology/Logger.java) - Ведет логи программы в файле
  log_client.txt. Реализован по шаблону Singleton. Потокобезопасен за счет Enum;
- [Settings.class](client/src/main/java/ru/netology/Settings.java) - Считывает данные из
  settings_client.properties. Там находятся данные о номере порта и т.д.