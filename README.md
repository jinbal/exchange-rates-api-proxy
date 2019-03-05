# exchange-rates-api-proxy
Coding challenge from Landoop


Create a REST application with a single endpoint :
````
POST /api/convert
Body:
{
 "fromCurrency": "GBP",
 "toCurrency" : "EUR",
 "amount" : 102.6
}
````
The return should be an object with the exchange rate between the "fromCurrency" to "toCurrency"
and the amount converted to the second curency.
````
{
 "exchange" : 1.11,
 "amount" : 113.886,
 "original" : 102.6
}
````
The exchange rates should be loaded from https://exchangeratesapi.io and assume the currency
rates change every 1 minute.

Implementation notes

Tests:
````
sbt clean test
````
Integration tests
````
sbt clean it:test
````

Run App
````
sbt assembly

java -jar target/scala-2.12/exchange-rates-api-proxy-assembly-0.1.jar

POST http://localhost:8080/api/convert
Body:
{
 "fromCurrency": "GBP",
 "toCurrency" : "EUR",
 "amount" : 102.6
}
````
