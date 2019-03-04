package com.jinbal.landoop

import java.time.LocalDate

package object domain {

  case class ConvertRequest(fromCurrency: String, toCurrency: String, amount: Double)

  case class ConvertResponse(exchange: Double, amount: Double, original: Double)

  case class ExchangeRates(base: String, date: LocalDate, rates: Map[String, Double])

  class ExchangeRateApiException(message: String) extends RuntimeException(message)

}
