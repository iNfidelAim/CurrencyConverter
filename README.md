# CurrencyConverter
CurrencyConverter

Тянет курсы валют с  ЦБ РФ через XML и конвертирует их. 
Записывает историю конвертаций и статистику.

Запуск из cmd командной mvnw spring-boot:run;
Для создания jar файла команда  mvnw clean package для Windows;
Запуск jar файла через cmd:  
в папке с jar файлом ввести команду:  java -jar currency-converter-0.0.1-SNAPSHOT.jar;
Открыть в браузере localhost:8080 - будет ошибка 404; 
Открыть в браузере localhost:8080/convert - будет ошибка 500 (сервер),  почему так я пока не понял (где-то что-то не дописал, надо разобраться);
