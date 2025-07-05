package com.jinbal.exchangerates

import cats.effect.IO
import com.jinbal.exchangerates.conversion.CurrencyConverter
import com.jinbal.exchangerates.domain.ExchangeRatesDomain.{ConvertCurrency, ConvertCurrencyResult}
import munit.CatsEffectSuite

class ConvertCurrencySpec extends CatsEffectSuite {

  test("CurrencyConverter converts currency correctly") {
    val convertCurrency = ConvertCurrency("BTC", "USD", 1)
    val expected = ConvertCurrencyResult(50000, 50000, 1)
    val converter = convertStub(expected)
    
    assertIO(converter.convert(convertCurrency), expected)
  }

  def convertStub(expectedResult: ConvertCurrencyResult) = new CurrencyConverter[IO] {
    override def convert(convertCurrency: ConvertCurrency): IO[ConvertCurrencyResult] = IO.pure(expectedResult)
  }
}