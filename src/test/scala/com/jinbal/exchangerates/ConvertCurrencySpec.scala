package com.jinbal.exchangerates

import cats.effect.IO
import com.jinbal.exchangerates.conversion.CurrencyConverter
import com.jinbal.exchangerates.domain.ExchangeRatesDomain.{ConvertCurrency, ConvertCurrencyResult}
import io.circe.generic.auto._
import munit.CatsEffectSuite
import org.http4s._
import org.http4s.circe.jsonOf
import org.http4s.implicits._

class ConvertCurrencySpec extends CatsEffectSuite {
  implicit val dec = jsonOf[IO, ConvertCurrencyResult]

  test("HelloWorld returns hello world message") {
    val convertCurrency = ConvertCurrency("BTC", "USD", 1)
    val expected = ConvertCurrencyResult(1, 2, 3)
    assertIO(makeConvertRequest(convertCurrency, expected).flatMap(_.as[ConvertCurrencyResult]), expected)
  }

  def convertStub(expectedResult: ConvertCurrencyResult) = new CurrencyConverter {
    override def convert(convertCurrency: ConvertCurrency): IO[ConvertCurrencyResult] = IO.pure(expectedResult)
  }

  private def makeConvertRequest(convertCurrency: ConvertCurrency,
                                 expectedResult: ConvertCurrencyResult): IO[Response[IO]] = {
    val queryParams = Map(
      "fromCurrency" -> convertCurrency.fromCurrency,
      "toCurrency" -> convertCurrency.toCurrency,
      "amount" -> convertCurrency.amount.toString
    )
    val getHW = Request[IO](Method.GET, uri"/convert".withQueryParams(queryParams))
    ExchangeRatesRoutes.currencyConversionRoutes(convertStub(expectedResult)).orNotFound(getHW)
  }
}