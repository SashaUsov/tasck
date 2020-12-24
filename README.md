Тестовое задание: REST API 

Необходимо реализовать REST API на Java, позволяющее отправить на него http POST запрос и получить ответ.

Требования:
1. Запрос и ответ в формате JSON.
2. Запрос, ответ и дату/время необходимо залогировать в отдельный файл.
3. Проект должен собираться в war, для установки на Tomcat.
4. Если в запросе содержится поле "id" со значением = 1, то ответ должен быть по примеру ниже, иначе ответ должен быть NULL.
5. Формат запроса POST.
6. Реализовать шифрование и дешифрование AES-256 входящего запроса и ответа. 
   Эту часть необходимо только залогировать. Например (часть лога):
   === encryption: sfdjnva9sfv87say9hdfow3
   === decryption: {"fio": "Test Testov"}

Пример запроса BODY:
{"id": 1}

Пример ответа BODY:
{"fio": "Test Testov"}

Рекомендуется использовать Maven, Spring (Rest Controller), Log4j. Остальное на усмотрение разработчика. 
Желательно выполнить максимальное количество требований :)

----------------

There are two branches in this project: master and avoidNull.
The master branch is made as much as possible in accordance with the requirements for the task.
The avoidNull branch has been changed since I consider it more correct to return some meaningful response with the corresponding status instead of null.

To simplify reading the logs of requests and responses, logging occurs only in the RequestHandlerService class, all other application logs are disabled.

When launching the project locally, you can send your request to the application as shown below:

curl --location --request POST 'http://localhost:8080/check/id' \
--header 'Content-Type: application/json' \
--data-raw '{
"id": 1
}'