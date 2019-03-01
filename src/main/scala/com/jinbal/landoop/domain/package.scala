package com.jinbal.landoop

package object domain {

  case class ConvertRequest(fromCurrency: String, toCurrency: String, amount: Double)

  case class ConvertResponse(exchange: Double, amount: Double, original: Double)

}
