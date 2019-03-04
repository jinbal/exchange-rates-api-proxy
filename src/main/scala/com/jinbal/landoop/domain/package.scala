package com.jinbal.landoop

import java.time.LocalDate

package object domain {

  case class ConvertCurrency(fromCurrency: String, toCurrency: String, amount: Double)

  case class ConvertCurrencyResult(exchange: Double, amount: Double, original: Double)

  case class ExchangeRates(base: String, date: LocalDate, rates: Map[String, Double])

  class ExchangeRateApiException(message: String) extends RuntimeException(message)

}
