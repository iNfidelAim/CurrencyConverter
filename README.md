# CurrencyConverter
CurrencyConverter

Тянет курсы валют с  ЦБ РФ через XML и конвертирует их в web интерфейсе. 
Записывает историю конвертаций и ведет небольшую статистику.

Запуск из cmd командной mvnw spring-boot:run;
Для создания jar файла команда  mvnw clean package для Windows;
Запуск jar файла через cmd:  
в папке с jar файлом ввести команду:  java -jar currency-converter-0.0.1-SNAPSHOT.jar;
Открыть в браузере localhost:8080 
