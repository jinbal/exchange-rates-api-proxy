# exchange-rate-sapi-proxy
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