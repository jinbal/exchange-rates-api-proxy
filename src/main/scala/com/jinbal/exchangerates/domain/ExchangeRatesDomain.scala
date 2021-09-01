package com.jinbal.exchangerates.domain

object ExchangeRatesDomain {

  case class ConvertCurrency(fromCurrency: String, toCurrency: String, amount: Double)

  case class ConvertCurrencyResult(exchange: Double, amount: Double, original: Double)

  case class ExchangeRates(currency: String, rates: Map[String, Double])

  case class ExchangeRatesData(data: ExchangeRates)

  class ExchangeRateApiException(message: String) extends RuntimeException(message)


}
